package org.examples.grpc.pubsub;

import java.io.IOException;
import java.util.logging.Logger;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class WebServer {

	private static final Logger logger = Logger.getLogger(WebServer.class.getName());
	private Server server;
	
	private void start() throws IOException {
		int port = 80;
		server = ServerBuilder.forPort(port).addService(new PubsubProxyImpl()).build().start();
		 Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				WebServer.this.stop();
				System.err.println("*** server shut down");
			}
		});
		logger.info("Server started, listening on " + port);
	}

	
	private void blockUntilShutdown() throws InterruptedException {
		if (server != null) {
			server.awaitTermination();
		}
	}

	private void stop() {
		if (server != null) {
			server.shutdown();
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		final WebServer server = new WebServer();
		server.start();
		server.blockUntilShutdown();
	}
}