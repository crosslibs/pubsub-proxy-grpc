package org.examples.grpc.pubsub.server;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;

/**
 * Intercept incoming calls
 * 
 * Any filteration, authentication etc can be plugged here
 */
public class GrpcRequestInterceptor implements ServerInterceptor {

	/**
	 * Special check in place only for getaccesstoken to validate client-id
	 * 
	 * For any subsequent requests where bearer token needs to be validated it 
	 * 
	 * should be offloaded to cloud endpoints
	 */
	public <ReqT, RespT> Listener<ReqT> interceptCall(
			ServerCall<ReqT, RespT> call, 
			Metadata requestHeaders,
			ServerCallHandler<ReqT, RespT> next) {
				
		ServerCall.Listener<ReqT> listener = next.startCall(call,requestHeaders);
		
		if(StringUtils.containsIgnoreCase(call.getMethodDescriptor().getFullMethodName(), "getaccesstoken")) 
		{			
			// Client id passed during proxy service start up time
			String savedClientId = System.getenv("CLIENTID");
			
			/**
			 * Client id received from user request. Request would be of the form: 
			 * 
			 * POST <endpoint>/getaccesstoken -H "Authorization: Basic <base-64 encoded client id>
			 */
			String receivedClientId = 
					new String(Base64.decodeBase64(requestHeaders
							.get(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER))
							.substring("Basic ".length())
							.getBytes()));
						
			// Do they match?
			if(receivedClientId.equals(savedClientId)) {
				return listener;
			}
			
			call.close(Status.UNAUTHENTICATED.withDescription("Unauthorized Client"), new Metadata());
		}
		
		return listener;
	}	
}