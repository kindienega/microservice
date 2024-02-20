FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder

# Set the working directory inside the container
# WORKDIR /app

# COPY pom.xml .

COPY "AuthService" /AuthService

# RUN mvn -f pom.xml  clean install 

COPY pom.xml /tmp/pom.xml
RUN mvn -B -f /tmp/pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve

FROM eclipse-temurin:17-jdk-jammy

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file from the builder stage to the runtime image
COPY --from=builder /app/module1/target/*.jar app.jar

# Expose any necessary ports
EXPOSE 8080

# Define the command to run the application
CMD ["java", "-jar", "app.jar"]