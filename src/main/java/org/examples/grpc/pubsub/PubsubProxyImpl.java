/* Copyright 2019 Google Inc. All rights reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License. */

package org.examples.grpc.pubsub;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;
import org.examples.grpc.pubsub.PubsubProxyServiceGrpc.PubsubProxyServiceImplBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage.Builder;
import com.google.pubsub.v1.PubsubMessage;
import io.grpc.stub.StreamObserver;

public class PubsubProxyImpl extends PubsubProxyServiceImplBase {

	private HashMap<String, Publisher> publishers = new HashMap<String, Publisher>();
	private static final Logger logger = Logger.getLogger(PubsubProxyImpl.class.getName());
	
	/**
	 * Publish Request
	 * @param request
	 * @param responseObserver
	 */
	public void publish(PublishRequest request, StreamObserver<PublishResponse> responseObserver) {
		try {
			String topic = request.getTopic();
			Publisher publisher = getPublisher(topic);
			publishMessage(publisher, request);
			PublishResponse reply = PublishResponse.newBuilder().build();
			responseObserver.onNext(reply);
			responseObserver.onCompleted();
		}
		catch (Exception ex) {
			logger.severe(ex.getMessage());			
		}
	}
	
	/**
	 * Populates PubSub publisher
	 * Publishes messages downstream
	 * @param publisher
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void publishMessage(Publisher publisher, PublishRequest request) throws Exception {
		final org.examples.grpc.pubsub.PubMessage message = request.getMessage();
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

		if (!message.getAttributes().isEmpty()) {
			builder.putAllAttributes(new ObjectMapper().readValue(message.getAttributes(), HashMap.class));
		}

		ApiFuture<String> future = publisher.publish(builder.build());
		ApiFutures.addCallback(future, new ApiFutureCallback<String>() {
			
			public void onFailure(Throwable throwable) {
				if (throwable instanceof ApiException) {
					ApiException apiException = ((ApiException) throwable);
					apiException.printStackTrace();
					logger.severe("Failed to publish message: " + apiException.getMessage());
				}
			}
			public void onSuccess(String msgId) {
				logger.info("Successfully published: " + msgId);
			}

		}, MoreExecutors.directExecutor());
	}

	/**
	 * Creates PubSub publisher if one doesn't exist
	 * @param topic
	 * @return
	 * @throws IOException
	 */
	private Publisher getPublisher(String topic) throws IOException {
		if (!publishers.containsKey(topic)) {
			synchronized (PubsubProxyImpl.class) {
				if (!publishers.containsKey(topic)) {
					logger.info("Creating new publisher for: " + topic);
					Publisher publisher = Publisher.newBuilder(ProjectTopicName.of(ServiceOptions.getDefaultProjectId(), topic)).build();
					publishers.put(topic, publisher);
				}
			}
		}
		return publishers.get(topic);
	}
	
	/**
	 * Health check- returns 200OK
	 * @param request
	 * @param responseObserver
	 */
	@Override
	public void getHealth(org.examples.grpc.pubsub.Void request,
			io.grpc.stub.StreamObserver<org.examples.grpc.pubsub.Void> responseObserver) {
		responseObserver.onCompleted();
	}
}