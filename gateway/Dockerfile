#gateway
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY ./target/gateway-1.0.1-SNAPSHOT.jar gateway.jar
ENV CONFIG_SERVER_HOST=config-server
ENTRYPOINT [ "java", "-jar", "/app/gateway.jar" ]