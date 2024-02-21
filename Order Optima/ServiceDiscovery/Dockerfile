
# Use a base image with Java installed
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app


COPY  target/service-discovery-0.0.1-SNAPSHOT.jar  /app/

EXPOSE 8761

# Specify the command to run your application
CMD ["java", "-jar", "/app/service-discovery-0.0.1-SNAPSHOT.jar"]