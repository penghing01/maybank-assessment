FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
COPY src /build/app/src
COPY pom.xml /build/app
RUN mvn -f /build/app/pom.xml clean package -DskipTests

FROM openjdk:21-jdk-slim

RUN groupadd -g 999 appuser && \
    useradd -r -u 999 -g appuser appuser

RUN mkdir app
WORKDIR /app
RUN chown appuser /app

USER appuser

COPY --from=build build/app/target/maybank-assessment-1.0.0-SNAPSHOT.jar /app/application.jar
CMD ["java", "-jar", "/app/application.jar"]