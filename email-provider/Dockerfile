#email-provider
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY ./target/email-provider-1.0.1-SNAPSHOT.jar email-provider.jar
ENV CONFIG_SERVER_HOST=config-server
ENTRYPOINT [ "java", "-jar", "/app/email-provider.jar" ]