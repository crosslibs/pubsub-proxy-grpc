FROM openjdk:8-jre
WORKDIR /
ADD target/pubsub-proxy-grpc-1.0-SNAPSHOT-jar-with-dependencies.jar pubsub-proxy-grpc-1.0-SNAPSHOT-jar-with-dependencies.jar
EXPOSE 50051
CMD java -jar pubsub-proxy-grpc-1.0-SNAPSHOT-jar-with-dependencies.jar 
