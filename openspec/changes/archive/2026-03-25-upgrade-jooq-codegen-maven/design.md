## Context

Das Projekt nutzt jOOQ als Abstraktionslayer für Datenbankoperationen. Die Version 3.20.11 ist in drei Artefakten definiert:

1. **Plugin** (`jooq-codegen-maven` 3.20.11): Generiert Java-Code aus dem Datenbankschema
2. **Runtime** (`jooq` 3.20.11): Abfragelogik und DSL zur Laufzeit
3. **Meta** (`jooq-meta` 3.20.11): Introspektions-API für die Code-Generierung

Der generierte Code liegt in `services/acme/src/generated/java/de/acme/jooq/` und wird von `LiedRepository` und `BenutzerRepository` sowie `UserScoreBoardRepository` verwendet.

Version 3.21.0 (Patch-Release) enthält Bugfixes und kleine Verbesserungen ohne Breaking Changes.

## Goals / Non-Goals

**Goals:**
- Alle drei jOOQ-Artefakte auf Version 3.21.0 aktualisieren
- Datenbankschema mit neuem jOOQ-Codegen regenerieren
- Alle Tests (Unit, Integration, E2E) erfolgreich bestehen
- Keine Verhaltensänderung in den Repositories – nur interne Code-Generierung aktualisieren

**Non-Goals:**
- Schema-Änderungen vornehmen
- Repository-API ändern
- neue Datenbankfeatures einführen
- Performance-Optimierungen durchführen (außer denen, die von jOOQ 3.21.0 automatisch kommen)

## Decisions

**Entscheidung 1: Versionierung-Synchronisation**

Alle drei jOOQ-Artefakte werden auf die gleiche Version 3.21.0 aktualisiert:
- `jooq-codegen-maven` → 3.21.0 (Plugin)
- `jooq-meta` → 3.21.0 (Transitive Abhängigkeit des Plugins)
- `jooq` → 3.21.0 (Runtime-Abhängigkeit)

**Begründung**: Dies folgt dem jOOQ Best-Practice, alle jOOQ-Komponenten auf der gleichen Version zu halten, um Kompatibilität zu gewährleisten.

**Alternativen betrachtet**:
- Version staggered aktualisieren: Nicht empfohlen, würde zu Inkompatibilitäten führen
- Nur Runtime aktualisieren: Nicht ausreichend – der Codegen-Plugin bestimmt die Code-Generation

**Entscheidung 2: Code-Regeneration Strategie**

Nach der Versionsaktualisierung wird der Code mittels `mvn clean compile` (oder `mvn org.jooq:jooq-codegen-maven:3.21.0:generate`) neu generiert.

**Begründung**: Die neue Version kann Codegen-Verbesserungen bringen; Neugeneration stellt sicher, dass der Code optimal ist.

**Entscheidung 3: Test-Strategie**

Nach Regeneration werden folgende Tests ausgeführt:
1. Unit-Tests: `mvn test`
2. Integrations-Tests: Abdeckung der Repository-Zugriffe
3. E2E-Tests: Validierung der gesamten Anwendung

**Begründung**: Patch-Releases sind normalerweise vollständig rückwärtskompatibel, aber Tests geben Sicherheit.

## Risks / Trade-offs

| Risiko | Wahrscheinlichkeit | Mitigation |
|--------|------------------|-----------|
| Generierter Code unterscheidet sich wesentlich | Niedrig | Tests decken Abweichungen auf |
| Inkompatibilität mit Spring Boot Starter JooQ | Niedrig | jOOQ 3.21.0 ist kompatibel mit Spring Boot 4.0.4 |
| Datenbankverbindung während Code-Generation | Mittel | Docker-Compose läuft in generate-sources Phase – keine zusätzlichen Schritte nötig |

## Migration Plan

**Schritt 1**: Dependency-Versions aktualisieren in `services/acme/pom.xml`

**Schritt 2**: Code regenerieren
```bash
cd services/acme
mvn clean compile
```

**Schritt 3**: Tests ausführen
```bash
mvn test
```

**Schritt 4**: Code-Review des generierten Codes durchführen (diff anschauen)

**Schritt 5**: Commit & Push

**Rollback**: Falls Probleme auftreten, Git-revert durchführen und auf 3.20.11 zurückgehen

## Open Questions

Keine offenen Fragen – dies ist ein geradliniges Patch-Update.
