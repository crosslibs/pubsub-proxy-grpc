package org.examples.grpc.pubsub.client;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.examples.grpc.pubsub.generated.PublishRequest;
import org.examples.grpc.pubsub.generated.PublishResponse;
import org.examples.grpc.pubsub.generated.PubsubMessage;
import org.examples.grpc.pubsub.generated.PubsubProxyServiceGrpc;
import org.examples.grpc.pubsub.generated.TokenRequest;
import org.examples.grpc.pubsub.generated.TokenResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;


public class Client {
	private static final Logger logger = Logger.getLogger(Client.class.getName());

	private final ManagedChannel channel;
	private final PubsubProxyServiceGrpc.PubsubProxyServiceBlockingStub blockingStub;

	/**
	 * Create gRPC client
	 * @param host
	 * @param port
	 * @throws Exception
	 * 
	 * Channels are secure by default (via SSL/TLS). 
	 * For the example we disable TLS to avoid needing certificates.
	 *
	 */
	public Client(String host, int port) throws Exception 
	{
		this(ManagedChannelBuilder.forAddress(host, port)
				.usePlaintext().build());
				
	}

	/**
	 * 
	 * @param channel
	 */
	Client(ManagedChannel channel) {
		this.channel = channel;
		blockingStub = PubsubProxyServiceGrpc.newBlockingStub(channel);
	}

	/**
	 * 
	 * @throws InterruptedException
	 */
	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

	/**
	 * Get access token example
	 */
	public void getAccessToken() {
		
		TokenRequest request = TokenRequest.newBuilder().build();
		TokenResponse response;
		
		try {
			response = blockingStub.getAccessToken(request);
		} 
		catch (StatusRuntimeException e) {
			logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
			return;
		}
		
		logger.info("Token: " + response.getToken());
	}

	/**
	 * Publish example
	 */
	public void publish() {
		
		PubsubMessage msg = PubsubMessage.newBuilder().setData("123").setMessageId("123").setAttributes("{\"key\": \"value\"}").build();
		PublishRequest pubreq = PublishRequest.newBuilder().setTopic("topic").setMessage(msg).build();
		PublishResponse pubresponse;
		
		try {
			pubresponse = blockingStub.publish(pubreq);
		}
		catch (StatusRuntimeException e) {
			logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
			return;
		}
		
		logger.info("Done publish: " + pubresponse.getMessageId());
	}
	
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		Client client = new Client("localhost", 80);
		try {
			client.getAccessToken();
			client.publish();
		} 
		finally {
			client.shutdown();
		}
	}
}