# demo/Dockerfile
FROM openjdk:21-jre-slim
WORKDIR /app
# Plik JAR, który jest tworzony przez Maven
ARG JAR_FILE=target/demo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]