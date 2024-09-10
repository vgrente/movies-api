import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.jetbrains.kotlin.plugin.allopen") version "2.0.0"
    id("org.graalvm.buildtools.native") version "0.10.2"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
    id("jacoco")
    id("org.sonarqube") version "5.1.0.4882"
    id("org.jetbrains.kotlinx.kover") version "0.8.3"
    kotlin("plugin.jpa") version "2.0.20"
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.spring") version "2.0.20"
}

group = "io.vgrente"
version = "0.0.1-SNAPSHOT"

jacoco {
    toolVersion = "0.8.12"
    reportsDirectory.set(layout.buildDirectory.dir("customJacocoReportDir"))
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
    implementation("org.redisson:redisson-hibernate-6:3.36.0")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("com.redis:testcontainers-redis:2.2.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

kover {
    reports {
        filters {
            excludes {
                classes("org.spring*")
            }
        }
        verify {
            rule {
                minBound(25)
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}
tasks.withType<BootBuildImage> {
    imageName.set("${project.group}/${rootProject.name}")
    builder.set("dashaun/builder:tiny")
}

allOpen {
    annotation("jakarta.persistence.Entity")
}

sonar {
    properties {
        property("sonar.projectKey", "movies-api")
    }
}
