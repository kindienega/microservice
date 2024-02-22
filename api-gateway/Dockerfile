# FROM openjdk:17-jdk-alpine

# WORKDIR /app


# COPY  target/Api_Gateway-0.0.1-SNAPSHOT.jar  /app/


# CMD ["java", "-jar", "/app/Api_Gateway-0.0.1-SNAPSHOT.jar"]

# EXPOSE 8181

FROM maven:3.9.6-eclipse-temurin-21-alpine as build
WORKDIR /app


COPY pom.xml ./api_gateway/

# Copy the source code
COPY src ./api_gateway/src
COPY ../pom.xml /app
# Build the application
RUN mvn package 

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

COPY --from=build /app/target/Api_Gateway-0.0.1-SNAPSHOT.jar  /app/app.jar

EXPOSE 8761

CMD ["java", "-jar" ,"app.jar"]