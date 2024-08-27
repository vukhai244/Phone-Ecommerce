FROM openjdk:17-jdk

LABEL authors="ngockhai"

WORKDIR /app

COPY target/cart-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8082

CMD ["java", "-jar", "app.jar"]