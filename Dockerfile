FROM eclipse-temurin:25-jdk-alpine AS builder

WORKDIR /workspace

COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle.kts settings.gradle.kts ./

RUN chmod +x ./gradlew

COPY src ./src

RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew --no-daemon bootJar -x test

FROM eclipse-temurin:25-jre-alpine AS runtime

RUN apk add --no-cache curl

WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring

COPY --from=builder --chown=spring:spring /workspace/build/libs/app.jar /app/app.jar

ENV SPRING_PROFILES_ACTIVE=prod
USER spring
EXPOSE 8080 8081

HEALTHCHECK --interval=30s --timeout=5s --start-period=30s \
  CMD ["curl", "-f", "http://localhost:8081/actuator/health/readiness"]

ENTRYPOINT ["java", "-XX:InitialRAMPercentage=50.0", "-XX:MaxRAMPercentage=75.0", "-XX:+UseG1GC", "-XX:+UseStringDeduplication", "-jar", "/app/app.jar"]
