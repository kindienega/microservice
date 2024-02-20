# Use a base image with Java installed
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app


COPY  target/auth-service-0.0.1-SNAPSHOT.jar  /app/

# Specify the command to run your application
CMD ["java", "-jar", "/app/auth-service-0.0.1-SNAPSHOT.jar"]