plugins {
    id("java")
    id("idea")
    id("org.springframework.boot") version "4.0.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.google.cloud.tools.jib") version "3.5.3"
}

group = "dev.harakki"
version = "0.0.1-SNAPSHOT"
description = "comics"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot Starters
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-security-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")

    // OpenAPI
    implementation(libs.springdoc.openapi.starter.webmvc.ui)
    implementation(libs.springdoc.openapi.starter.webmvc.scalar)

    // Amazon AWS S3
    implementation(libs.awssdk.s3)

    // Specification API
    implementation(libs.specification.arg.resolver)

    // Other Libraries
    implementation(libs.icu4j)
    implementation(libs.mapstruct)
    implementation(libs.slugify)
    implementation(libs.uuid.creator)

    // Bill of Materials
    implementation(platform(libs.spring.modulith.bom))

    // Spring Modulith
    implementation("org.springframework.modulith:spring-modulith-starter-core")
    implementation("org.springframework.modulith:spring-modulith-starter-jpa")
    runtimeOnly("org.springframework.modulith:spring-modulith-actuator")
    runtimeOnly("org.springframework.modulith:spring-modulith-observability")


    // Database
    runtimeOnly("org.postgresql:postgresql")

    // Dev
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")

    // Lombok and annotation processors
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor(libs.hibernate.jpamodelgen)
    annotationProcessor(libs.lombok.mapstruct.binding)
    annotationProcessor(libs.mapstruct.processor)

    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-actuator-test")
    testImplementation("org.springframework.boot:spring-boot-starter-batch-test")
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
    testImplementation("org.springframework.boot:spring-boot-starter-security-oauth2-resource-server-test")
    testImplementation("org.springframework.boot:spring-boot-starter-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-validation-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.springframework.modulith:spring-modulith-starter-test")
    testImplementation("org.testcontainers:testcontainers-junit-jupiter")
    testImplementation("org.testcontainers:testcontainers-postgresql")
    testImplementation(libs.mockito.core)
    testImplementation(libs.testcontainers.minio)
    testImplementation(libs.testcontainers.keycloak)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

jib {
    from {
        image = "eclipse-temurin:25-jre-alpine"
    }
    to {
        image = "harakki-comics"
        tags = setOf("latest")
    }
    container {
        ports = listOf("8080")
    }
}
