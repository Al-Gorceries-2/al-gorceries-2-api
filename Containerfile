FROM maven:3.9.2-eclipse-temurin-21 AS builder
WORKDIR /app
COPY ./pom.xml ./
COPY ./src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre-alpine-3.23
COPY --from=builder /app/target/*.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
