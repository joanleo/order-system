# Build stage
FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Package stage
FROM eclipse-temurin:17-jdk
COPY --from=build /target/ordersystem-0.0.1.jar app.jar
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
