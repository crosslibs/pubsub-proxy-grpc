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

	public Client(String host, int port) throws Exception {
		this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());

	}
	
	Client(ManagedChannel channel) {
		this.channel = channel;
		blockingStub = PubsubProxyServiceGrpc.newBlockingStub(channel);
	}

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

	@SuppressWarnings("unused")
	public void publish() {
		PubMessage msg = PubMessage.newBuilder()
				.setData("<data>")
				.setMessageId("<message-id>")
				.setAttributes("{\"key\": \"value\"}")
				.build();
		PublishRequest pubreq = PublishRequest.newBuilder()
				.setTopic("test")
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