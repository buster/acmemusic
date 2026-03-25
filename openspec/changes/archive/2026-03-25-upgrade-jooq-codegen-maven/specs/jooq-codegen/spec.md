## MODIFIED Requirements

### Requirement: jOOQ Code-Generierung

Das System MUSS mit jOOQ 3.21.0 (aktualisiert von 3.20.11) Code aus dem Datenbankschema generieren und diesen Code in die Java-Quellen integrieren.

#### Scenario: Code wird mit neuer Version generiert
- **WHEN** Maven die `generate-sources` Phase ausführt
- **THEN** jooq-codegen-maven 3.21.0 liest das PostgreSQL-Schema aus der Datenbank
- **AND** generiert aktualisierten Code nach `services/acme/src/generated/java/de/acme/jooq/`
- **AND** der generierte Code ist vollständig kompatibel mit jooq 3.21.0 Runtime

#### Scenario: Repositories können generierten Code nutzen
- **WHEN** LiedRepository, BenutzerRepository oder UserScoreBoardRepository den generierten Code aufrufen
- **THEN** alle jOOQ-DSL-Operationen funktionieren ordnungsgemäß
- **AND** keine Compilierungsfehler treten auf
- **AND** Datenbankabfragen liefern erwartete Ergebnisse

#### Scenario: Tests validieren Kompatibilität
- **WHEN** Unit-, Integrations- und E2E-Tests ausgeführt werden
- **THEN** alle Tests passen
- **AND** keine Regressionsfehler entstehen durch die Code-Generierung
- **AND** die jOOQ-Migration ist transparent für Geschäftslogik

### Requirement: Versionskonsistenz aller jOOQ-Artefakte

Das System MUSS alle jOOQ-Komponenten auf die gleiche Version (3.21.0) halten.

#### Scenario: Alle Versionen sind synchronisiert
- **WHEN** das Projekt kompiliert wird
- **THEN** `jooq-codegen-maven` ist Version 3.21.0
- **AND** `jooq-meta` ist Version 3.21.0
- **AND** `jooq` ist Version 3.21.0
- **AND** keine Versions-Konflikte entstehen

#### Scenario: Maven-Build ist erfolgreich
- **WHEN** `mvn clean compile` ausgeführt wird
- **THEN** alle Versionen sind aktualisiert
- **AND** Build abgeschlossen ohne Fehler
- **AND** generierter Code ist im Projekt vorhanden
