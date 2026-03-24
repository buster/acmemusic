## Context

The ACME music player application currently runs on Spring Boot 3.5.5 with Java 21, jOOQ 3.19.18, and Jackson 2.x. The application uses a modular architecture with separate services/modules, Maven as build tool, and PostgreSQL with Liquibase for database management. Spring Boot 3.5.5 reaches end-of-support in May 2026, necessitating an upgrade to Spring Boot 4.0.4 which brings Jakarta EE 11, Jackson 3, and jOOQ 3.20.11.

Key constraints:
- Project already uses Java 21 (no compiler changes needed)
- Multi-module Maven project (root pom.xml, services/acme/pom.xml, library modules)
- jOOQ code generation integrated in build pipeline
- Jackson used for REST API serialization in Spring Boot application
- Existing tests and integration suites must remain functional
- JaCoCo code coverage configuration excludes jOOQ generated classes

## Goals / Non-Goals

**Goals:**
- Upgrade Spring Boot from 3.5.5 to 4.0.4 with all direct dependencies aligned
- Migrate Jackson from version 2.x to 3.x with package name changes
- Update jOOQ codegen plugin to 3.20.11 and regenerate all code
- Ensure all existing tests pass without functional changes
- Document migration steps for future reference
- Maintain backward compatibility of REST APIs and domain models

**Non-Goals:**
- Refactor domain logic or service architecture
- Add new application features during upgrade
- Change testing framework or test structure
- Optimize performance (though upgrade may provide benefits)
- Migrate to alternative persistence frameworks
- Break existing REST API contracts

## Decisions

### Decision 1: Maven Dependency Management Strategy
**Chosen approach**: Centralized version management via Spring Boot parent BOM, with explicit overrides only where necessary.

**Rationale**: Spring Boot parent provides curated dependency versions. By upgrading to Spring Boot 4.0.4, we inherit tested and compatible versions for Jackson (3.x), jOOQ (3.20.11), PostgreSQL JDBC (42.7.10), and other key dependencies. This minimizes version conflicts and ensures ecosystem compatibility.

**Alternatives considered**:
- Manual version management per dependency: High risk of version conflicts and incompatibilities
- Mixed approach (some managed, some explicit): Increases maintenance burden and confusion

**Implementation**:
- Update `<version>4.0.4</version>` in services/acme/pom.xml parent reference
- Remove explicit version declarations that are now managed by Spring Boot 4.0.4 (e.g., PostgreSQL JDBC)
- Keep explicit jOOQ codegen plugin version `3.20.11` (not managed by parent)

### Decision 2: Jackson Migration Path
**Chosen approach**: Two-phase migration—first update Maven dependencies with package name changes, then fix import statements in source code.

**Rationale**: Jackson 3 requires package migration from `com.fasterxml.jackson.*` to `tools.jackson.*`. Maven dependency groupId changes from `com.fasterxml.jackson.core` to `tools.jackson.core`. This is a breaking change requiring coordinated updates across pom.xml and source files.

**Alternatives considered**:
- Gradual migration with dual Jackson versions: Not viable; Spring Boot 4 standardizes on Jackson 3
- Direct code replacement: Risk of missed imports or incomplete migration

**Implementation**:
- Update all Jackson-related dependencies in pom.xml to `tools.jackson.*` groupId
- Replace all imports in source code from `com.fasterxml.jackson` to `tools.jackson`
- Run tests to verify serialization/deserialization works correctly
- Focus areas: REST controllers returning JSON, JSON configuration classes, any custom serializers/deserializers

### Decision 3: jOOQ Code Regeneration
**Chosen approach**: Regenerate all jOOQ code using jOOQ 3.20.11 codegen plugin as part of build process.

**Rationale**: jOOQ 3.20 introduces behavior changes for dirty flag tracking (distinguishing touched vs. modified fields). Existing generated code from 3.19.18 must be regenerated to benefit from these improvements and avoid compatibility issues.

**Alternatives considered**:
- Manually patching generated code: Error-prone and unmaintainable
- Using 3.19.18 codegen with 3.20.11 runtime: Risk of undefined behavior at runtime

**Implementation**:
- Update `jooq-codegen-maven` plugin version to `3.20.11` in services/acme/pom.xml
- Run `mvn clean jooq-codegen:generate` to regenerate all code in services/acme/src/generated/
- Verify generated classes are updated (check file timestamps and class structure)
- Run integration tests to ensure database operations work with new generated code
- Commit regenerated code as separate artifact

### Decision 4: Liquibase and PostgreSQL JDBC Compatibility
**Chosen approach**: Allow Spring Boot 4.0.4 to manage Liquibase Core version while allowing explicit PostgreSQL JDBC version to be managed.

**Rationale**: Spring Boot 4.0.4 includes compatible versions of Liquibase (checked against project's current 4.31.1). PostgreSQL JDBC 42.7.10 (managed by Spring Boot 4.0.4) is compatible with PostgreSQL 12+ and Liquibase 4.x. No explicit overrides needed unless testing reveals issues.

**Alternatives considered**:
- Explicitly pinning Liquibase version: Unnecessary if Spring Boot version is compatible
- Removing PostgreSQL JDBC version declaration: Good approach; let Spring Boot manage it

**Implementation**:
- Remove explicit `42.7.8` PostgreSQL JDBC declaration if present
- Keep Liquibase Maven Plugin at `4.31.1` unless Spring Boot upgrade reveals incompatibility
- Test database connectivity and migrations in integration tests

### Decision 5: Testing and Validation
**Chosen approach**: Preserve all existing tests; validate migration success through test execution before and after upgrade.

**Rationale**: Existing unit tests, integration tests, component tests, and E2E tests provide the safety net for migration. If tests pass, the upgrade is functionally correct. JUnit 5, Cucumber, and Testcontainers remain compatible.

**Alternatives considered**:
- Minimal testing during upgrade: High risk of silent failures
- Rewriting tests during upgrade: Out of scope; mixing concerns

**Implementation**:
- Run `mvn clean test` after each major step (Spring Boot version, Jackson migration, jOOQ regeneration)
- Run `mvn clean verify` for full integration test suite
- Run component tests with `mvn -f services/acme/pom.xml test` focused on affected modules
- Verify E2E tests pass with `cd e2e && mvn clean test`

## Risks / Trade-offs

**Risk 1: Jackson 3 Package Changes Break Compilation**
→ Mitigation: Use regex find-replace in IDE to migrate all imports. Compile to catch missed imports. Focus on REST controllers and serialization configurations.

**Risk 2: jOOQ Generated Code Behavior Changes Cause Runtime Issues**
→ Mitigation: Comprehensive integration test execution immediately after regeneration. Dirty flag behavior changes primarily affect record lifecycle; tests should cover create/update/delete operations.

**Risk 3: Removed Spring Boot 3.x Deprecations Break Application Startup**
→ Mitigation: Review Spring Boot 4.0 migration guide for removed APIs. Test application startup and basic flows. Potential issues with configuration properties; search for deprecated property names in YAML files.

**Risk 4: Testcontainers Compatibility**
→ Mitigation: Current versions (1.19.8 / 1.21.3) should be compatible. If issues arise, update to latest compatible version. Spring Boot 4 typically works with recent Testcontainers releases.

**Risk 5: Multi-module Dependency Alignment**
→ Mitigation: After upgrading root and services/acme pom.xml, verify all library modules (libs/common-api, libs/events, etc.) compile without version conflicts. Maven dependency tree analysis if needed.

**Trade-off**: Upgrade requires coordinated changes across multiple pom.xml files and source code. This increases complexity but is a one-time effort with clear benefit (end-of-support date extension to at least 2028).

## Migration Plan

**Phase 1: Dependency Updates (tasks)**
1. Update `spring-boot-starter-parent` version in `services/acme/pom.xml` to `4.0.4`
2. Update `jooq-codegen-maven` plugin version to `3.20.11`
3. Remove explicit PostgreSQL JDBC version (allow Spring Boot to manage)
4. Run `mvn clean compile` to identify other version conflicts

**Phase 2: Jackson Migration (tasks)**
1. Update Jackson dependency groupIds from `com.fasterxml.jackson` to `tools.jackson` in all pom.xml files
2. Search for and replace Jackson imports in source code
3. Run `mvn clean compile` to verify no import errors
4. Run `mvn test` to verify serialization/deserialization

**Phase 3: jOOQ Code Regeneration (tasks)**
1. Run `mvn -f services/acme/pom.xml clean jooq-codegen:generate`
2. Verify generated code in `services/acme/src/generated/java/de/acme/jooq/` is updated
3. Run `mvn -f services/acme/pom.xml test` to verify generated code works
4. Commit regenerated code

**Phase 4: Full Test Suite (tasks)**
1. Run all unit tests: `mvn clean test`
2. Run integration tests: `mvn clean verify`
3. Run component tests: `mvn -f services/acme/pom.xml test -Dgroups=componenttest`
4. Run E2E tests: `cd e2e && mvn clean test`

**Phase 5: Documentation and Sign-off (tasks)**
1. Update project documentation if needed
2. Add notes on Jackson 3 migration for future developers
3. Verify application starts and basic functionality works

**Rollback Strategy**: If critical issues arise after deployment, revert to Spring Boot 3.5.5 by reverting all pom.xml changes and regenerating jOOQ code with 3.19.18 plugin.

## Open Questions

1. Are there any custom Jackson serializers/deserializers in the codebase that need special handling for Jackson 3?
2. Does the application use any Spring Boot 3.x deprecated properties that will cause startup failures in 4.0?
3. Is Undertow used as an embedded server, or is the default Tomcat/Netty sufficient? (Undertow was dropped in Spring Boot 4)
4. Are there any additional external dependencies in the application that depend on specific Spring Boot 3.x versions?
