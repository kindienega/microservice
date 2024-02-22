# Use a base image with Java and Maven pre-installed
FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app

COPY ../pom.xml .
COPY pom.xml ./User_Service/

# Copy the source code
COPY src ./User_Service/src

# Build the application
RUN mvn clean install -f User_Service/pom.xml -DskipTests

# Set the base image for the final image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/User_Service/target/User_Service.jar ./User_Service.jar
EXPOSE 8786
# Set the command to run the application
CMD ["java", "-jar", "User_Service.jar"]