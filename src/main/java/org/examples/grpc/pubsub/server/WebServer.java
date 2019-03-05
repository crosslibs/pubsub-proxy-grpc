package org.examples.grpc.pubsub.server;

import java.io.IOException;
import java.util.logging.Logger;

import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 *  gRPC server with config:
 *  
 *  Port:80
 *  
 *  Resources: Endpoints (/getaccesstoken, /publish, /health)
 *  
 *  Refer PubsubProxyImpl for details
 */
public class WebServer {

	private static final Logger logger = Logger.getLogger(WebServer.class.getName());
	private Server server;

	private void start() throws IOException {

		// Port on which the server should run
		int port = 80;

		// Any heavy lifting at the time of server startup?
		ServerInit.preStartup();
		
		// Start server 
		server = ServerBuilder.forPort(port)
				.addService(new PubsubProxyImpl())
				.intercept(new GrpcRequestInterceptor())
				.build().start();
		 
		Runtime.getRuntime().addShutdownHook(new Thread() {
			// Use stderr here since the logger 
			// may have been reset by its JVM shutdown hook.
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