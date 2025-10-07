# Multi-stage Dockerfile: build with Maven, run on Eclipse Temurin JRE 21
FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /workspace

# Copy Maven wrapper and pom early to leverage layer caching
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Copy sources
COPY src ./src

# Build the application (skip tests for faster image builds by default)
RUN mvn -B -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built jar from the builder stage. Adjust name if your artifactId/version differ.
COPY --from=builder /workspace/target/invoicing-roi-simulator-0.0.1-SNAPSHOT.jar /app/app.jar

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JAVA_OPTS=""

EXPOSE 8080

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
