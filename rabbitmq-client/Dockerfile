FROM openjdk:17-jdk

LABEL authors="ngockhai"

WORKDIR /app

COPY target/rabbitmq-client-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8001

CMD ["java", "-jar", "app.jar"]