## ADDED Requirements

### Requirement: jOOQ 3.20.11 Code Generation
The application SHALL generate jOOQ data access code using jOOQ codegen plugin version 3.20.11, leveraging updated code generation features and jOOQ 3.20.11 runtime behavior.

#### Scenario: jOOQ codegen plugin is version 3.20.11
- **WHEN** Maven builds services/acme module with jooq-codegen-maven plugin
- **THEN** plugin version is explicitly set to 3.20.11; generated code is created in services/acme/src/generated/java/de/acme/jooq/

#### Scenario: Generated code compiles against jOOQ 3.20.11 runtime
- **WHEN** generated code in services/acme/src/generated/ is used in source code
- **THEN** code compiles without errors; jOOQ 3.20.11 runtime library is available on classpath

### Requirement: Dirty Flag Behavior with Touched vs. Modified
The generated jOOQ record classes SHALL distinguish between touched fields (any field accessed) and modified fields (fields changed from their original values), enabling more efficient update operations.

#### Scenario: Record tracks touched fields separately from modified fields
- **WHEN** a jOOQ record is created and field values are accessed without modification
- **THEN** touched flag is set for accessed fields; modified flag remains unset for unchanged fields

#### Scenario: Partial updates use modified flag information
- **WHEN** an update operation executes on a record
- **THEN** only fields marked as modified are included in the UPDATE statement; unchanged fields are excluded

#### Scenario: Database integration tests verify dirty flag behavior
- **WHEN** integration tests run with regenerated jOOQ code
- **THEN** all database operations (insert, update, delete, select) work correctly with new dirty flag semantics

### Requirement: Generated Code Regeneration
All jOOQ-generated classes in services/acme/src/generated/java/de/acme/jooq/ SHALL be regenerated using jOOQ 3.20.11 codegen to replace code generated with jOOQ 3.19.18.

#### Scenario: Generated files are recreated by codegen
- **WHEN** mvn jooq-codegen:generate runs on services/acme module
- **THEN** all .java files in src/generated/java/de/acme/jooq/ are regenerated; class signatures and method signatures reflect jOOQ 3.20.11 generation

#### Scenario: Generated record classes match current database schema
- **WHEN** codegen runs against PostgreSQL database with current Liquibase migrations
- **THEN** generated classes include all current tables (BENUTZER, LIED, BENUTZER_AUSZEICHNUNGEN, BENUTZER_SCORE_BOARD) with correct columns and types

### Requirement: JaCoCo Code Coverage Configuration
The application's code coverage configuration SHALL continue to exclude jOOQ-generated classes from coverage reporting.

#### Scenario: JaCoCo excludes jOOQ generated code
- **WHEN** coverage report is generated via jacoco:report goal
- **THEN** files in src/generated/java/de/acme/jooq/** are excluded from coverage calculations; coverage results focus on business logic
