#config-server
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY ./target/config-server-1.0.1-SNAPSHOT.jar config-server.jar
ENTRYPOINT [ "java", "-jar", "/app/config-server.jar" ]