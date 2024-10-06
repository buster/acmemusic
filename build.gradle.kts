plugins {
    java
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.jooq.jooq-codegen-gradle") version "3.19.13"
}

group = "de.acme"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
//    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//    implementation("com.h2database:h2")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
//    testImplementation("org.testcontainers:postgresql:1.20.1")
    testImplementation("org.postgresql:postgresql:42.7.3")
    implementation("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
//    runtimeOnly("com.h2database:h2")
    implementation("org.springframework.boot:spring-boot-docker-compose")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // cucumber
    testImplementation("io.cucumber:cucumber-java:7.18.1")
    testImplementation("io.cucumber:cucumber-junit:7.18.1")
    testImplementation("io.cucumber:cucumber-spring:7.18.1")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.18.1")

    testImplementation("org.junit.platform:junit-platform-suite")

    testImplementation("org.assertj:assertj-core:3.26.3")
    implementation("org.liquibase:liquibase-core")

    implementation("org.jooq:jooq")
    jooqCodegen("org.postgresql:postgresql:42.7.3")
}

tasks.withType<Test> {
    jvmArgs("-noverify", "-XX:+EnableDynamicAgentLoading", "-Djdk.instrument.traceUsage")
    systemProperty("cucumber.junit-platform.naming-strategy", "long")
    useJUnitPlatform()
}

jooq {
    configuration {
        jdbc {
            driver = "org.postgresql.Driver"
            url = "jdbc:postgresql://localhost:5432/acmedb"
            user = "acme"
            password = "acme"

        }
        generator {
            database {
                name = "org.jooq.meta.postgres.PostgresDatabase"
                inputSchema = "public"
                excludes = """
                    databasechangelog.*
                """
            }
            generate { }
            target {
                packageName = "de.acme.jooq"
                directory = "src/main/java"
            }
        }

    }
}