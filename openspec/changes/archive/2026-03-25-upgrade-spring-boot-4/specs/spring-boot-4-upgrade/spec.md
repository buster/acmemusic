## ADDED Requirements

### Requirement: Spring Boot 4.0.4 Parent Version
The application SHALL be built with Spring Boot parent version 4.0.4, providing access to Jakarta EE 11, Java 21 baseline enforcement, and compatible versions of all core Spring Boot dependencies.

#### Scenario: Application builds with Spring Boot 4.0.4
- **WHEN** Maven builds the project with services/acme/pom.xml referencing spring-boot-starter-parent 4.0.4
- **THEN** build completes successfully with all Spring Boot dependencies resolved from Spring Boot 4.0.4 BOM

#### Scenario: Java 21 is enforced as baseline
- **WHEN** project properties specify maven.compiler.source/target as 21
- **THEN** compilation enforces Java 21 language features and libraries; Java 20 or earlier compilation fails

### Requirement: Jakarta EE 11 Servlet Compatibility
The application SHALL be compatible with Jakarta EE 11 Servlet API version 6.1, ensuring web layer functionality with Jakarta-based frameworks.

#### Scenario: Web application uses Jakarta EE 11
- **WHEN** Spring Boot 4.0.4 is used with web starter
- **THEN** application deploys with Servlet 6.1 API available; HTTP requests/responses work correctly

#### Scenario: Servlet imports use jakarta.servlet
- **WHEN** any Java code imports servlet classes
- **THEN** imports use jakarta.servlet.* (not javax.servlet.*); application compiles and runs correctly

### Requirement: Dependency Version Alignment
All direct dependencies SHALL be aligned with Spring Boot 4.0.4 managed versions, eliminating version conflicts and ensuring ecosystem compatibility.

#### Scenario: PostgreSQL JDBC is version 42.7.10 or later
- **WHEN** application connects to PostgreSQL database
- **THEN** driver version is 42.7.10 (managed by Spring Boot 4.0.4); database connections work correctly

#### Scenario: All transitive dependencies resolve without conflicts
- **WHEN** Maven resolves the dependency tree for services/acme module
- **THEN** no dependency version conflicts are reported; mvn dependency:tree completes successfully

### Requirement: Removed Spring Boot 3.x Deprecated APIs Handled
The application SHALL not use any Spring Boot APIs or configuration properties removed in Spring Boot 4.0, ensuring clean migration without startup failures.

#### Scenario: Application starts without deprecated API errors
- **WHEN** application starts with Spring Boot 4.0.4
- **THEN** no error messages about removed deprecated APIs or properties; application reaches "started in X seconds" log message

#### Scenario: application.yml uses only valid Spring Boot 4.0 properties
- **WHEN** Spring Boot loads application.yml configuration
- **THEN** all property names are recognized by Spring Boot 4.0.4; no "unknown property" warnings appear in logs
