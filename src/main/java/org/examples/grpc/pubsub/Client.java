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

/**
 * Client that issues a 
 * pubsub request to the {@link WebServer}.
 */
package org.examples.grpc.pubsub;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.examples.grpc.pubsub.PubsubProxyServiceGrpc.PubsubProxyServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class Client {
	private static final Logger logger = Logger.getLogger(Client.class.getName());
	private final ManagedChannel channel;
	private final PubsubProxyServiceBlockingStub blockingStub;

	/**
	 * Construct client connecting to WebServer at {@code host:port}.  
	 * @param host
	 * @param port
	 * @throws Exception
	 */
	public Client(String host, int port) throws Exception {
		this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());

	}
	
	/**
	 * Construct client for accessing WebServer using the existing channel. 
	 * @param channel
	 */
	Client(ManagedChannel channel) {
		this.channel = channel;
		blockingStub = PubsubProxyServiceGrpc.newBlockingStub(channel);
	}

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

	/**
	 * Publish messages
	 */
	@SuppressWarnings("unused")
	public void publish() {
		// Replace placeholders appropriately
		PubMessage msg = PubMessage.newBuilder()
				.setData("<DATA>")
				.setMessageId("<MESSAGE-ID>")
				.setAttributes("{\"KEY\": \"VALUE\"}")
				.build();
		PublishRequest pubreq = PublishRequest.newBuilder()
				.setTopic("<TOPIC-NAME>")
				.setMessage(msg).build();
		try {
			PublishResponse pubresponse = blockingStub.publish(pubreq);
		} 
		catch (StatusRuntimeException e) {
			logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
			return;
		}
		logger.info("Async publish done! Check server logs for details");
	}

	public static void main(String[] args) throws Exception {
		Client client = new Client("localhost", 80);
		try {
			client.publish();
		} finally {
			client.shutdown();
		}
	}
}