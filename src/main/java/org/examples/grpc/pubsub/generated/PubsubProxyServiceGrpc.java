package org.examples.grpc.pubsub.generated;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * Proxy service definition
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.17.1)",
    comments = "Source: proxy.proto")
public final class PubsubProxyServiceGrpc {

  private PubsubProxyServiceGrpc() {}

  public static final String SERVICE_NAME = "org.examples.grpc.pubsub.generated.PubsubProxyService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.examples.grpc.pubsub.generated.Void,
      org.examples.grpc.pubsub.generated.Void> getGetHealthMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetHealth",
      requestType = org.examples.grpc.pubsub.generated.Void.class,
      responseType = org.examples.grpc.pubsub.generated.Void.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.examples.grpc.pubsub.generated.Void,
      org.examples.grpc.pubsub.generated.Void> getGetHealthMethod() {
    io.grpc.MethodDescriptor<org.examples.grpc.pubsub.generated.Void, org.examples.grpc.pubsub.generated.Void> getGetHealthMethod;
    if ((getGetHealthMethod = PubsubProxyServiceGrpc.getGetHealthMethod) == null) {
      synchronized (PubsubProxyServiceGrpc.class) {
        if ((getGetHealthMethod = PubsubProxyServiceGrpc.getGetHealthMethod) == null) {
          PubsubProxyServiceGrpc.getGetHealthMethod = getGetHealthMethod = 
              io.grpc.MethodDescriptor.<org.examples.grpc.pubsub.generated.Void, org.examples.grpc.pubsub.generated.Void>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "org.examples.grpc.pubsub.generated.PubsubProxyService", "GetHealth"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.examples.grpc.pubsub.generated.Void.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.examples.grpc.pubsub.generated.Void.getDefaultInstance()))
                  .setSchemaDescriptor(new PubsubProxyServiceMethodDescriptorSupplier("GetHealth"))
                  .build();
          }
        }
     }
     return getGetHealthMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.examples.grpc.pubsub.generated.TokenRequest,
      org.examples.grpc.pubsub.generated.TokenResponse> getGetAccessTokenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAccessToken",
      requestType = org.examples.grpc.pubsub.generated.TokenRequest.class,
      responseType = org.examples.grpc.pubsub.generated.TokenResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.examples.grpc.pubsub.generated.TokenRequest,
      org.examples.grpc.pubsub.generated.TokenResponse> getGetAccessTokenMethod() {
    io.grpc.MethodDescriptor<org.examples.grpc.pubsub.generated.TokenRequest, org.examples.grpc.pubsub.generated.TokenResponse> getGetAccessTokenMethod;
    if ((getGetAccessTokenMethod = PubsubProxyServiceGrpc.getGetAccessTokenMethod) == null) {
      synchronized (PubsubProxyServiceGrpc.class) {
        if ((getGetAccessTokenMethod = PubsubProxyServiceGrpc.getGetAccessTokenMethod) == null) {
          PubsubProxyServiceGrpc.getGetAccessTokenMethod = getGetAccessTokenMethod = 
              io.grpc.MethodDescriptor.<org.examples.grpc.pubsub.generated.TokenRequest, org.examples.grpc.pubsub.generated.TokenResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "org.examples.grpc.pubsub.generated.PubsubProxyService", "GetAccessToken"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.examples.grpc.pubsub.generated.TokenRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.examples.grpc.pubsub.generated.TokenResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new PubsubProxyServiceMethodDescriptorSupplier("GetAccessToken"))
                  .build();
          }
        }
     }
     return getGetAccessTokenMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.examples.grpc.pubsub.generated.PublishRequest,
      org.examples.grpc.pubsub.generated.PublishResponse> getPublishMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Publish",
      requestType = org.examples.grpc.pubsub.generated.PublishRequest.class,
      responseType = org.examples.grpc.pubsub.generated.PublishResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.examples.grpc.pubsub.generated.PublishRequest,
      org.examples.grpc.pubsub.generated.PublishResponse> getPublishMethod() {
    io.grpc.MethodDescriptor<org.examples.grpc.pubsub.generated.PublishRequest, org.examples.grpc.pubsub.generated.PublishResponse> getPublishMethod;
    if ((getPublishMethod = PubsubProxyServiceGrpc.getPublishMethod) == null) {
      synchronized (PubsubProxyServiceGrpc.class) {
        if ((getPublishMethod = PubsubProxyServiceGrpc.getPublishMethod) == null) {
          PubsubProxyServiceGrpc.getPublishMethod = getPublishMethod = 
              io.grpc.MethodDescriptor.<org.examples.grpc.pubsub.generated.PublishRequest, org.examples.grpc.pubsub.generated.PublishResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "org.examples.grpc.pubsub.generated.PubsubProxyService", "Publish"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.examples.grpc.pubsub.generated.PublishRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.examples.grpc.pubsub.generated.PublishResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new PubsubProxyServiceMethodDescriptorSupplier("Publish"))
                  .build();
          }
        }
     }
     return getPublishMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PubsubProxyServiceStub newStub(io.grpc.Channel channel) {
    return new PubsubProxyServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PubsubProxyServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PubsubProxyServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PubsubProxyServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PubsubProxyServiceFutureStub(channel);
  }

  /**
   * <pre>
   * Proxy service definition
   * </pre>
   */
  public static abstract class PubsubProxyServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Health check
     * </pre>
     */
    public void getHealth(org.examples.grpc.pubsub.generated.Void request,
        io.grpc.stub.StreamObserver<org.examples.grpc.pubsub.generated.Void> responseObserver) {
      asyncUnimplementedUnaryCall(getGetHealthMethod(), responseObserver);
    }

    /**
     * <pre>
     * Get access token for authentication
     * </pre>
     */
    public void getAccessToken(org.examples.grpc.pubsub.generated.TokenRequest request,
        io.grpc.stub.StreamObserver<org.examples.grpc.pubsub.generated.TokenResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAccessTokenMethod(), responseObserver);
    }

    /**
     * <pre>
     * Publish message to Google Cloud PubSub
     * </pre>
     */
    public void publish(org.examples.grpc.pubsub.generated.PublishRequest request,
        io.grpc.stub.StreamObserver<org.examples.grpc.pubsub.generated.PublishResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPublishMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetHealthMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.examples.grpc.pubsub.generated.Void,
                org.examples.grpc.pubsub.generated.Void>(
                  this, METHODID_GET_HEALTH)))
          .addMethod(
            getGetAccessTokenMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.examples.grpc.pubsub.generated.TokenRequest,
                org.examples.grpc.pubsub.generated.TokenResponse>(
                  this, METHODID_GET_ACCESS_TOKEN)))
          .addMethod(
            getPublishMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.examples.grpc.pubsub.generated.PublishRequest,
                org.examples.grpc.pubsub.generated.PublishResponse>(
                  this, METHODID_PUBLISH)))
          .build();
    }
  }

  /**
   * <pre>
   * Proxy service definition
   * </pre>
   */
  public static final class PubsubProxyServiceStub extends io.grpc.stub.AbstractStub<PubsubProxyServiceStub> {
    private PubsubProxyServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PubsubProxyServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PubsubProxyServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PubsubProxyServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Health check
     * </pre>
     */
    public void getHealth(org.examples.grpc.pubsub.generated.Void request,
        io.grpc.stub.StreamObserver<org.examples.grpc.pubsub.generated.Void> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetHealthMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Get access token for authentication
     * </pre>
     */
    public void getAccessToken(org.examples.grpc.pubsub.generated.TokenRequest request,
        io.grpc.stub.StreamObserver<org.examples.grpc.pubsub.generated.TokenResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAccessTokenMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Publish message to Google Cloud PubSub
     * </pre>
     */
    public void publish(org.examples.grpc.pubsub.generated.PublishRequest request,
        io.grpc.stub.StreamObserver<org.examples.grpc.pubsub.generated.PublishResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPublishMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Proxy service definition
   * </pre>
   */
  public static final class PubsubProxyServiceBlockingStub extends io.grpc.stub.AbstractStub<PubsubProxyServiceBlockingStub> {
    private PubsubProxyServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PubsubProxyServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PubsubProxyServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PubsubProxyServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Health check
     * </pre>
     */
    public org.examples.grpc.pubsub.generated.Void getHealth(org.examples.grpc.pubsub.generated.Void request) {
      return blockingUnaryCall(
          getChannel(), getGetHealthMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Get access token for authentication
     * </pre>
     */
    public org.examples.grpc.pubsub.generated.TokenResponse getAccessToken(org.examples.grpc.pubsub.generated.TokenRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetAccessTokenMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Publish message to Google Cloud PubSub
     * </pre>
     */
    public org.examples.grpc.pubsub.generated.PublishResponse publish(org.examples.grpc.pubsub.generated.PublishRequest request) {
      return blockingUnaryCall(
          getChannel(), getPublishMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Proxy service definition
   * </pre>
   */
  public static final class PubsubProxyServiceFutureStub extends io.grpc.stub.AbstractStub<PubsubProxyServiceFutureStub> {
    private PubsubProxyServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PubsubProxyServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PubsubProxyServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PubsubProxyServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Health check
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.examples.grpc.pubsub.generated.Void> getHealth(
        org.examples.grpc.pubsub.generated.Void request) {
      return futureUnaryCall(
          getChannel().newCall(getGetHealthMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Get access token for authentication
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.examples.grpc.pubsub.generated.TokenResponse> getAccessToken(
        org.examples.grpc.pubsub.generated.TokenRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAccessTokenMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Publish message to Google Cloud PubSub
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.examples.grpc.pubsub.generated.PublishResponse> publish(
        org.examples.grpc.pubsub.generated.PublishRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPublishMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_HEALTH = 0;
  private static final int METHODID_GET_ACCESS_TOKEN = 1;
  private static final int METHODID_PUBLISH = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PubsubProxyServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PubsubProxyServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_HEALTH:
          serviceImpl.getHealth((org.examples.grpc.pubsub.generated.Void) request,
              (io.grpc.stub.StreamObserver<org.examples.grpc.pubsub.generated.Void>) responseObserver);
          break;
        case METHODID_GET_ACCESS_TOKEN:
          serviceImpl.getAccessToken((org.examples.grpc.pubsub.generated.TokenRequest) request,
              (io.grpc.stub.StreamObserver<org.examples.grpc.pubsub.generated.TokenResponse>) responseObserver);
          break;
        case METHODID_PUBLISH:
          serviceImpl.publish((org.examples.grpc.pubsub.generated.PublishRequest) request,
              (io.grpc.stub.StreamObserver<org.examples.grpc.pubsub.generated.PublishResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class PubsubProxyServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PubsubProxyServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.examples.grpc.pubsub.generated.PubsubProxyProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PubsubProxyService");
    }
  }

  private static final class PubsubProxyServiceFileDescriptorSupplier
      extends PubsubProxyServiceBaseDescriptorSupplier {
    PubsubProxyServiceFileDescriptorSupplier() {}
  }

  private static final class PubsubProxyServiceMethodDescriptorSupplier
      extends PubsubProxyServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PubsubProxyServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PubsubProxyServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PubsubProxyServiceFileDescriptorSupplier())
              .addMethod(getGetHealthMethod())
              .addMethod(getGetAccessTokenMethod())
              .addMethod(getPublishMethod())
              .build();
        }
      }
    }
    return result;
  }
}
