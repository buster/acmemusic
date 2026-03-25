## Warum

Das Projekt nutzt derzeit jOOQ 3.20.11. Ein Update auf Version 3.21.0 wird notwendig, um von neueren Bugfixes, Verbesserungen der Code-Generierung und potenziellen Performance-Optimierungen zu profitieren. Dies ist eine Standard-Patch-Update zur Wartung von Abhängigkeiten.

## Was ändert sich

- Aktualisierung von `org.jooq:jooq-codegen-maven` von Version 3.20.11 auf 3.21.0 in `services/acme/pom.xml`
- Aktualisierung der Abhängigkeit `org.jooq:jooq-meta` von Version 3.20.11 auf 3.21.0
- Aktualisierung der Direktabhängigkeit `org.jooq:jooq` von Version 3.20.11 auf 3.21.0
- Regeneration des jOOQ-Codes mit der neuen Version
- Verifizierung, dass alle Tests weiterhin erfolgreich laufen

## Fähigkeiten

### Neue Fähigkeiten

Entfällt – dies ist ein Wartungs-Update mit Patch-Version-Bumps.

### Geänderte Fähigkeiten

- `jooq-codegen`: Die jOOQ-Code-Generierung wird auf Version 3.21.0 aktualisiert. Das Generated-Code-Verzeichnis wird neu generiert, um von verbesserten Code-Generierungsfeatures zu profitieren.

## Auswirkungen

- **Betroffener Code**: 
  - `services/acme/pom.xml` – Plugin- und Abhängigkeitsversionierung
  - `services/acme/src/generated/java/de/acme/jooq/` – Vollständig regenerierter jOOQ-Code
  - Alle JDBC-Repository-Implementierungen, die jOOQ nutzen
  
- **Abhängigkeiten**: Aktualisierung von drei jOOQ-Artefakten (jooq-codegen-maven, jooq-meta, jooq)

- **Tests**: Alle Unit- und Integrationstests müssen ausgeführt werden, um sicherzustellen, dass die neue Code-Generierung kompatibel ist
