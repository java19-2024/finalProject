#avg-reducer
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY ./target/avg-reducer-1.0.1-SNAPSHOT.jar avg-reducer.jar
ENV CONFIG_SERVER_HOST=config-server
ENTRYPOINT [ "java", "-jar", "/app/avg-reducer.jar" ]