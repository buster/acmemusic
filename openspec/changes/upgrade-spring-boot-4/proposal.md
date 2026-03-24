## Why

Spring Boot 3.5.5 reaches end-of-support in May 2026. Upgrading to Spring Boot 4.0.4 provides access to latest security patches, performance improvements, and ecosystem advancements. Spring Boot 4 introduces Jakarta EE 11, Java 21 baseline, and Jackson 3 with improved type handling and performance, positioning the application for long-term stability and feature support.

## What Changes

- **Spring Boot parent version**: `3.5.5` → `4.0.4`
- **Java baseline**: Explicitly required Java 21 (project already uses Java 21 per `.sdkmanrc`)
- **jOOQ runtime and codegen**: `3.19.18` → `3.20.11` (with behavior changes for dirty flag tracking)
- **Jackson framework**: Version 2.x → 3.x with package/groupId changes (`com.fasterxml.jackson` → `tools.jackson`)
- **Jakarta EE baseline**: Moved to Jakarta EE 11 and Servlet 6.1
- **Removed deprecated APIs**: Many properties and APIs removed that were deprecated in Spring Boot 3.x
- **PostgreSQL JDBC driver**: `42.7.8` → `42.7.10` (managed by Spring Boot 4.0.4)
- **Kotlin**: Baseline moved to Kotlin 2.2 (if applicable)
- **BREAKING**: All Jackson imports must migrate from `com.fasterxml.jackson.*` to `tools.jackson.*`
- **BREAKING**: jOOQ-generated code must be regenerated (dirty flag behavior changes)
- **BREAKING**: Removed Spring Boot 3.x deprecations may require application code updates

## Capabilities

### New Capabilities
- `spring-boot-4-upgrade`: Support for Spring Boot 4.0.4 with Jakarta EE 11, Jackson 3, and jOOQ 3.20.11

### Modified Capabilities
- `persistence-jooq-codegen`: jOOQ code generation behavior changes with dirty flag tracking (touched vs. modified) requiring regeneration
- `jackson-serialization`: Jackson 3 package/groupId migration and API updates

## Impact

- **Maven dependencies**: `pom.xml` files (root, services/acme) require version updates
- **Build plugins**: jOOQ codegen plugin version updated
- **Generated code**: All jOOQ-generated classes in `services/acme/src/generated/java/de/acme/jooq/**` must be regenerated
- **Java source code**: All files using Jackson directly require import migration (`com.fasterxml.jackson` → `tools.jackson`)
- **Spring configurations**: Any configurations using removed deprecated properties/APIs
- **Testcontainers and Docker**: May require version compatibility checks
- **Jakarta EE compatibility**: Ensures Servlet 6.1 compatibility for web layer
