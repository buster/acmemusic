plugins {
    java
    idea
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.jooq.jooq-codegen-gradle") version "3.19.13"
    id("org.liquibase.gradle") version "2.2.0"
}

group = "de.acme"
version = "0.0.1-SNAPSHOT"
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    sourceSets.main {
        java.srcDir("src/generated/java")
    }
}

idea {
    module {
        generatedSourceDirs =
            generatedSourceDirs + file("src/generated/java")
    }
}

repositories {
    mavenCentral()
}

val mockitoVersion = "5.14.1"
val cucumberVersion = "7.19.0"
extra["mockito.version"] = "$mockitoVersion"
extra["byte-buddy.version"] = "1.15.3"
dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("io.github.wimdeblauwe:htmx-spring-boot:3.5.0")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
//    implementation("org.springframework.boot:spring-boot-starter-security")
//    implementation("org.springframework.session:spring-session-jdbc")
//    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
//    testImplementation("org.springframework.security:spring-security-test")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-docker-compose")
    implementation("org.postgresql:postgresql:42.7.3")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Testing
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("io.cucumber:cucumber-java:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-junit:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-spring:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:$cucumberVersion")
    testImplementation("org.junit.platform:junit-platform-suite")
    testImplementation("org.mockito:mockito-junit-jupiter:$mockitoVersion")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("org.assertj:assertj-core:3.26.3")
    testImplementation("com.microsoft.playwright:playwright:1.47.0")
    // Jooq
    implementation("org.jooq:jooq")
    jooqCodegen("org.postgresql:postgresql:42.7.3")

    // Liquibase + Plugin
    implementation("org.liquibase:liquibase-core")
    liquibaseRuntime("org.postgresql:postgresql:42.7.3")
    liquibaseRuntime("org.liquibase:liquibase-core")
    liquibaseRuntime("info.picocli:picocli:4.7.5")

    // Extra
    implementation("org.projectlombok:lombok")
    implementation("com.google.guava:guava:33.3.1-jre")

    // Web
    runtimeOnly("org.webjars.npm:htmx.org:2.0.2")
}

tasks.withType<Test> {
    jvmArgs(
//        "-noverify",
        "-XX:+EnableDynamicAgentLoading",
        "-Djdk.instrument.traceUsage",
    )
    systemProperty("cucumber.junit-platform.naming-strategy", "long")
    useJUnitPlatform()
}

tasks.register<Exec>("startPostgres") {
    workingDir = file("${project.rootDir}")
    commandLine("docker-compose", "up", "-d", "database")
}

liquibase {
    activities.register("main") {
        this.arguments = mapOf(
            "logLevel" to "info",
            "classpath" to "${project.rootDir}/src/main/",
            "changelogFile" to "resources/db/changelog/db.changelog-master.xml",
            "url" to "jdbc:postgresql://localhost:5432/acmedb",
            "username" to "acme",
            "password" to "acme",
            "driver" to "org.postgresql.Driver"
        )
    }
    runList = "main"
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
                directory = "src/generated/java"

            }
        }

    }
}

tasks.get("jooqCodegen").dependsOn("dropAll", "update")
tasks.get("update").dependsOn("startPostgres")
