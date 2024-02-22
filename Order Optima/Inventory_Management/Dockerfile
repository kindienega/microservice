FROM openjdk:17-jdk-alpine

WORKDIR /app


COPY  target/inventory_management-0.0.1-SNAPSHOT.jar  /app/

EXPOSE 8086
CMD ["java", "-jar", "/app/inventory_management-0.0.1-SNAPSHOT.jar"]