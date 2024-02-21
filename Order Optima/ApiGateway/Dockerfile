FROM openjdk:17-jdk-alpine

WORKDIR /app


COPY  target/Api_Gateway-0.0.1-SNAPSHOT.jar  /app/

EXPOSE 8181

CMD ["java", "-jar", "/app/Api_Gateway-0.0.1-SNAPSHOT.jar"]