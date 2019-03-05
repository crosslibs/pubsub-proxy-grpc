package org.examples.grpc.pubsub.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.examples.grpc.pubsub.generated.PublishRequest;
import org.examples.grpc.pubsub.generated.PublishResponse;
import org.examples.grpc.pubsub.generated.PubsubProxyServiceGrpc;
import org.examples.grpc.pubsub.generated.TokenRequest;
import org.examples.grpc.pubsub.generated.TokenResponse;
import org.examples.grpc.pubsub.utils.PublishMessageUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.api.gax.rpc.ApiException;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.PubsubMessage.Builder;

import io.grpc.stub.StreamObserver;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * gRPC service implementation
 * 
 * RPCs implemented below:
 * 
 * 1) /getaccesstoken: Validates the client id and generates a JWT signed by service account. This JWT is used to authenticate calls to /publish
 * 
 * 2) /pulish: Publishes messages downstream to google cloud pubsub 
 * 
 * 3) /health: Health check for glb/ingress. Returns 200Ok.
 * 
 */
public class PubsubProxyImpl extends PubsubProxyServiceGrpc.PubsubProxyServiceImplBase {
	// Locally store topic-to-publisher handler
	private HashMap<String, Publisher> publishers = new HashMap<String, Publisher>();
	private static final Logger logger = Logger.getLogger(PubsubProxyImpl.class.getName());
	/**
	 * 
	 * Get JWT access token signed by user passed service account
	 * 
	 * A pre-authorization check is enforced on this method. Refer
	 * GrpcRequestInterceptor for more details
	 * 
	 * JWT claims:
	 * 
	 * subject: service-account client email id
	 * 
	 * expiry: 3600 sec
	 * 
	 * issued-at: current system time
	 * 
	 * issuer: service-account client email id
	 * 
	 * audience: google cloud endpoint hosting transcoded RPCs
	 * 
	 */
	@Override
	public void getAccessToken(TokenRequest req, StreamObserver<TokenResponse> responseObserver) {
		try {
			JwtBuilder jwts = Jwts.builder();
			ServiceAccountCredentials serviceAccount = ServerInit.getServiceAccount();

			// Set header
			Map<String, Object> header = new HashMap<String, Object>();
			header.put("type", "JWT");
			header.put("alg", "RS256");
			jwts.setHeader(header);

			// Set claims
			Map<String, Object> claims = new HashMap<String, Object>();
			claims.put("sub", serviceAccount.getClientEmail());
			claims.put("exp", System.currentTimeMillis() / 1000 + 3600);
			claims.put("iat", System.currentTimeMillis() / 1000);
			claims.put("iss", serviceAccount.getClientEmail());
			claims.put("aud", "https://pubsub-proxy.endpoints."+ServerInit.getProjectId()+".cloud.goog");
			
			jwts.setClaims(claims);

			// Sign with key
			jwts.signWith(SignatureAlgorithm.RS256, serviceAccount.getPrivateKey());

			TokenResponse reply = TokenResponse.newBuilder().setToken(jwts.compact()).build();

			responseObserver.onNext(reply);
			responseObserver.onCompleted();
		}
		catch (Exception e) {
			e.printStackTrace();
			// TODO
		}
	}
	
	/**
	 * 
	 * Publish message downstream to Google Cloud Pubsub
	 * Example user request :
	 * 
	 * POST <endpoint>/publish -H "Authorization Bearer <>" 
	 * 
	 * -H "Content-Type: application/json" 
	 * 
	 * -d '{ "topic": "test", "message": { "attributes": '{
	 * 
	 * "key1": "value1", "key2" : "value2" ... }', "data":
	 * 
	 * "SGVsbG8gQ2xvdWZXNzYWdlIQ==", "messageId": "123", "publishTime":
	 * 
	 * "...timestamp..." } }'
	 * 
	 * Note: "attributes" expects a string not json - this is due to cloud endpoint 
	 * 
	 * limitation for transcoded APIs. Map proto cannot be converted to http spec
	 * 
	 */
	@Override
	public void publish(PublishRequest request, StreamObserver<PublishResponse> responseObserver) {

		try {
			String topic = request.getTopic();

			// Get publisher
			Publisher publisher = getPublisher(topic);

			// Publish message 
			publishMessage(publisher, request);

			// Optional - can add a message id as well below 
			// (PublishResponse.newBuilder().addMessageId().build()) 
			// received from async pubsub request
			PublishResponse reply = PublishResponse.newBuilder().build();
			
			responseObserver.onNext(reply);
			responseObserver.onCompleted();

		}
		catch (Exception ex) {
			//TODO
			ex.printStackTrace();
		}
	}
	/**
	 * 
	 * @param publisher
	 * @param pubsubMessage
	 */
	@SuppressWarnings("unchecked")
	private void publishMessage(Publisher publisher, PublishRequest request) throws Exception {
		final org.examples.grpc.pubsub.generated.PubsubMessage message = request.getMessage();
		Builder builder = PubsubMessage.newBuilder();
		
		if (!message.getData().isEmpty()) {
			builder.setData(ByteString.copyFromUtf8(message.getData()));
		}

		if (!message.getMessageId().isEmpty()) {
			builder.setMessageId(message.getMessageId());
		}

		if (null != message.getPublishTime()) {
			builder.setPublishTime(message.getPublishTime());
		}
		// Convert received string to Map<String, String>
		// Transcoded APIs do not support map<string, string> 
		if (!message.getAttributes().isEmpty()) {
			builder.putAllAttributes(new ObjectMapper().readValue(message.getAttributes(), HashMap.class));
		}
		// Async call to PubSub
		ApiFuture<String> future = publisher.publish(builder.build());
		ApiFutures.addCallback(future, new ApiFutureCallback<String>() {
			// Failed to publish messages downstream
			public void onFailure(Throwable throwable) {
				if (throwable instanceof ApiException) {
					ApiException apiException = ((ApiException) throwable);
					logger.severe("Failed to publish message: " + apiException.getMessage());

					// Write failed msgs to a sink: BQ in this case
					PublishMessageUtils.insertFailedMessagesInBQ(message, apiException);
				}
			}
			// Successfully published messages downstream
			public void onSuccess(String msgId) {
				logger.info("Successfully published: " + msgId);
			}

		}, MoreExecutors.directExecutor());
	}

	/**
	 * A long living publisher object needed. Creating 
	 * new publisher on each request would be extremely 
	 * expensive resulting in performance overhead
	 * 
	 * @param topic
	 * @return
	 * @throws Exception
	 */
	private Publisher getPublisher(String topic) throws IOException {
		// Check if publisher against a topic already exists
		if (!publishers.containsKey(topic)) {
			// Double checked locking to prevent any race conditions on publisher creation
			synchronized (PubsubProxyImpl.class) {
				if (!publishers.containsKey(topic)) {
					logger.info("Creating new publisher for: " + topic);
					// Get publisher
					Publisher publisher = Publisher
							.newBuilder(ProjectTopicName.of(
									ServerInit.getProjectId(), topic)).build();

					// Save publisher for later use
					publishers.put(topic, publisher);

				}
			}
		}
		return publishers.get(topic);
	}
	
	/**
	 * Health check 
	 * Do nothing - Just return 200OK
	 */
	@Override
	public void getHealth(org.examples.grpc.pubsub.generated.Void request,
			io.grpc.stub.StreamObserver<org.examples.grpc.pubsub.generated.Void> responseObserver) {
		responseObserver.onCompleted();
	}
}
