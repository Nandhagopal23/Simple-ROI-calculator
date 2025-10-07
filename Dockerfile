## Multi-stage Dockerfile for building and running the Spring Boot app
# Build stage: use Maven with Temurin JDK 21 to compile and create the fat jar
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /workspace

# Copy pom and wrapper first for better caching
COPY pom.xml mvnw .mvn/ ./
COPY src ./src

RUN chmod +x mvnw || true
RUN ./mvnw -DskipTests package -e

# Runtime stage: smaller JRE image
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the repackaged jar from the build stage
ARG JAR_FILE=target/invoicing-roi-simulator-0.0.1-SNAPSHOT.jar
COPY --from=build /workspace/${JAR_FILE} /app/app.jar

EXPOSE 8080

# Allow platform to pass JVM options via JAVA_OPTS env var
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
