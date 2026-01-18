# =========================
# Build stage
# =========================
FROM gradle:8.5-jdk21 AS build
WORKDIR /app

# Copy gradle config trước để cache dependency
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Download dependencies (cache layer)
RUN ./gradlew dependencies --no-daemon

# Copy source code
COPY src src

# Build Spring Boot jar
RUN ./gradlew bootJar --no-daemon

# =========================
# Runtime stage
# =========================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
