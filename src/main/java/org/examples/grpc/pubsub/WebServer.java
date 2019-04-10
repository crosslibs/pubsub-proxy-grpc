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
import java.util.logging.Logger;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class WebServer {

	private static final Logger logger = Logger.getLogger(WebServer.class.getName());
	private Server server;
	
	/**
	 * Start server at port 80
	 * @throws IOException
	 */
	private void start() throws IOException {
		int port = 80;
		server = ServerBuilder.forPort(port).addService(new PubsubProxyImpl()).build().start();
		 Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				 // Use stderr here since the logger may have been reset by its JVM shutdown hook.
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				WebServer.this.stop();
				System.err.println("*** server shut down");
			}
		});
		logger.info("Server started, listening on " + port);
	}

	/**
	 * Await termination on the main thread since the grpc 
	 * library uses daemon threads
	 * @throws InterruptedException
	 */
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