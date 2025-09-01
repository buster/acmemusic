# Implementation Plan: Modernisierung der Teststrategie

## Metadata

- **Story ID**: ACME-001
- **Created**: 2025-08-08
- **Status**: DRAFT
- **Estimated Effort**: 8 story points
- **Developer**: Roo

## Business Context

### Business Value

Das Ziel dieser Modernisierung ist die Verbesserung der Wartbarkeit, Klarheit und Effektivität der automatisierten Tests. Durch die Umstellung auf eine schichtenbasierte Testpyramide mit dedizierten, modernen Frameworks wird die Testausführung beschleunigt, die Fehleranalyse vereinfacht und die allgemeine Codequalität erhöht. Dies führt zu einer höheren Entwicklerproduktivität und stabileren Releases.

### Feature Description

Basierend auf der vorangegangenen Analyse wird die bestehende Teststrategie (T2T, T2R, R2R mit Cucumber) zu einer modernen, schichtenbasierten Testpyramide migriert. Bestehende Testtypen werden durch spezifische Frameworks ersetzt, um eine klare Trennung der Testebenen zu gewährleisten und die jeweiligen Stärken der Tools optimal zu nutzen.

### Acceptance Criteria

- [ ] AC1: Die Migration der T2R-Tests (Test-to-Real) zu Adapter-Integrationstests unter Verwendung von `@DataJpaTest` ist erfolgreich abgeschlossen.
- [ ] AC2: Die konzeptionell geplanten R2T-Tests (Real-to-Test) sind als Controller-/API-Tests mit `@WebMvcTest` implementiert.
- [ ] AC3: Die R2R-Tests (Real-to-Real) sind in ein separates Playwright-Modul für End-to-End-Tests migriert.
- [ ] AC4: Die T2T-Tests (Test-to-Test) mit Cucumber für Komponententests bleiben erhalten und sind in die neue Struktur integriert.
- [ ] AC5: Die Projektdokumentation, insbesondere `testing-ports-and-adapters.png` und relevante `adoc`-Dateien, ist aktualisiert und spiegelt die neue Teststrategie wider.
- [ ] AC6: Alte Testverzeichnisse (`test2real`, `real2real`) und Konfigurationen sind entfernt.

## Technical Analysis

### Architecture Alignment

- **Domain**: Übergreifend, betrifft die Testarchitektur des gesamten Projekts.
- **Use Cases**: Nicht zutreffend, da es sich um eine- **refactoring**-Maßnahme der Testinfrastruktur handelt.
- **Adapters Required**: Keine neuen produktiven Adapter, aber die Test-Adapter und -Konfigurationen werden grundlegend überarbeitet.
- **Documentation to Review**:
    - [x] `@/documentation/ADRs/02-testing-framework.adoc`
    - [x] `@/documentation/componenttesting.adoc`
    - [x] `@/documentation/testing-ports-and-adapters.png`
    - [x] `@/pom.xml` (zur Analyse der Test-Dependencies)

### Dependencies

- **External Systems**: Keine.
- **Internal Components**: Betrifft alle Komponenten mit Tests (`musicplayer`, `scoreboard`, `users`).
- **New Libraries**:
    - Playwright für E2E-Tests.
    - Test-bezogene Spring Boot Starter (`spring-boot-starter-test`).

## Implementation Steps

### Phase 1: Vorbereitung und Setup

```
INSTRUCTION: Schaffung der Grundlage für die neue Teststruktur.
```

- [x] **Step 1.1**: Erstelle neue Verzeichnisstrukturen für die verschiedenen Testebenen (`src/test/java/de/acme/musicplayer/api`, `src/test/java/de/acme/musicplayer/integration`).
**Abweichung:** Die Verzeichnisnamen wurden gemäß Task-Vorgabe angepasst.
- [x] **Step 1.2**: Passe die `pom.xml` an, um die notwendigen Abhängigkeiten für `@DataJpaTest`, `@WebMvcTest` und Playwright hinzuzufügen.
- [x] **Step 1.3**: Konfiguriere das Maven Failsafe/Surefire Plugin, um die neuen Testtypen korrekt zu erkennen und auszuführen (z.B. Integrationstests in der `verify`-Phase).
- [x] **Step 1.4**: Stelle sicher, dass die bestehende Test-Suite weiterhin vollständig ausgeführt werden kann.

### Phase 2: Migration der T2R zu Adapter-Integrationstests

```
INSTRUCTION: Migriere Datenbank-nahe Tests zu dedizierten @DataJpaTest-Tests.
```

- [x] **Step 2.1**: Identifiziere alle bestehenden T2R-Tests im Verzeichnis `componenttests/.../test2real`.
- [ ] **Step 2.2**: Migriere den ersten T2R-Test zu einem `@DataJpaTest` im neuen `adaptertests`-Verzeichnis.
- [ ] **Step 2.3**: Nutze Testcontainer (`Testcontainers`) für eine isolierte H2- oder PostgreSQL-Datenbank.
- [ ] **Step 2.4**: Führe den neuen Test aus und stelle sicher, dass er die gleiche Funktionalität abdeckt wie der alte Test.
- [ ] **Step 2.5**: Wiederhole den Prozess für alle verbleibenden T2R-Tests.
- [ ] **Step 2.6**: Lösche die alten T2R-Tests nach erfolgreicher Migration.

### Phase 3: Migration der R2T zu API-Tests

```
INSTRUCTION: Erstelle @WebMvcTest-Tests für die REST-Controller.
```

- [ ] **Step 3.1**: Identifiziere die REST-Controller, die getestet werden sollen (z.B. in `.../adapters/web/`).
- [ ] **Step 3.2**: Erstelle einen ersten `@WebMvcTest` für einen einfachen GET-Endpunkt.
- [ ] **Step 3.3**: Mocke die Service-Abhängigkeiten des Controllers mit `@MockBean`.
- [ ] **Step 3.4**: Validiere den HTTP-Status, die Header und den Response-Body.
- [ ] **Step 3.5**: Implementiere Tests für POST/PUT-Endpunkte und validiere die Request-Bodies und Fehlerfälle (z.B. 400 Bad Request).
- [ ] **Step 3.6**: Wiederhole den Prozess für alle relevanten Controller und Endpunkte.

### Phase 4: Erstellung des E2E-Test-Moduls (Playwright)

```
INSTRUCTION: Isoliere die E2E-Tests in einem eigenen Maven-Modul.
```

- [ ] **Step 4.1**: Erstelle ein neues Maven-Modul `e2e-tests`.
- [ ] **Step 4.2**: Konfiguriere das Modul für Playwright-Tests (inkl. `com.microsoft.playwright:playwright` Abhängigkeit und `playwright-maven-plugin`).
- [ ] **Step 4.3**: Migriere die bestehenden R2R-Tests aus `real2real` in das neue Playwright-Modul.
- [ ] **Step 4.4**: Passe die Tests an, sodass sie gegen eine laufende Instanz der Anwendung (via Docker Compose oder gestarteten Service) ausgeführt werden können.
- [ ] **Step 4.5**: Integriere die Ausführung der E2E-Tests in den Maven-Build-Prozess (z.B. in einem separaten Profil).

### Phase 5: Aufräumen

```
INSTRUCTION: Entferne alle Überreste der alten Teststrategie.
```

- [ ] **Step 5.1**: Lösche die alten Testverzeichnisse `test2real` und `real2real` aus allen Komponenten.
- [ ] **Step 5.2**: Entferne nicht mehr benötigte Cucumber-bezogene Konfigurationen, die ausschließlich für die alten Testtypen verwendet wurden.
- [ ] **Step 5.3**: Überprüfe die `pom.xml` auf veraltete Test-Abhängigkeiten und entferne sie.
- [ ] **Step 5.4**: Führe den gesamten Build (`mvn clean verify`) aus und stelle sicher, dass alle Tests erfolgreich durchlaufen.

### Phase 6: Dokumentation aktualisieren

```
INSTRUCTION: Passe die Projektdokumentation an die neue Strategie an.
```

- [ ] **Step 6.1**: Aktualisiere die `testing-ports-and-adapters.png`-Grafik, um die neuen Testebenen (Adapter, API, E2E) darzustellen.
- [ ] **Step 6.2**: Überarbeite `componenttesting.adoc` und andere relevante `adoc`-Dateien, um die neuen Frameworks und Verzeichnisstrukturen zu beschreiben.
- [ ] **Step 6.3**: Erstelle ein neues Dokument oder einen Abschnitt, der die E2E-Teststrategie mit Playwright erklärt.
- [ ] **Step 6.4**: Aktualisiere den `README.adoc`, falls sich die Befehle zur Ausführung der Tests geändert haben.

### Phase 7: Review und Abschluss

```
INSTRUCTION: Finale Überprüfung und Abschluss der Migration.
```

- [ ] **Step 7.1**: Führe eine Code-Review des gesamten Changesets durch.
- [ ] **Step 7.2**: Präsentiere die neue Teststrategie und die Ergebnisse dem Team.
- [ ] **Step 7.3**: Sammle Feedback und führe ggf. letzte Anpassungen durch.
- [ ] **Step 7.4**: Merge die Änderungen in den Hauptentwicklungszweig.
- [ ] **Step 7.5**: Aktualisiere den Status der Story ACME-001 zu `COMPLETED`.


## Quality Checkpoints

### After Each Phase

- [ ] Alle Tests der jeweiligen Ebene sind erfolgreich implementiert und laufen grün.
- [ ] Die Code-Coverage ist stabil oder verbessert.
- [ ] Die neue Struktur ist konsistent und nachvollziehbar.
- [ ] Die Prinzipien der Testpyramide sind eingehalten (wenige E2E-, mehr Integrations-, viele Komponententests).

### Definition of Done

- [ ] Alle Akzeptanzkriterien sind erfüllt.
- [ ] Alle neuen und migrierten Tests sind automatisiert und laufen erfolgreich im CI-Build.
- [ ] Der Code wurde einem Review unterzogen.
- [ ] Die gesamte Test- und Projektdokumentation ist auf dem neuesten Stand.
- [ ] Es wurde kein technisches Schuld eingeführt; alte Strukturen wurden vollständig entfernt.
- [ ] Die Änderungen sind in der Testumgebung deployed und verifiziert.
- [ ] Die Story ACME-001 ist im Tracking-System abgeschlossen.

## Progress Tracking

### Current Status

- **Current Phase**: Phase 2
- **Current Step**: Start
- **Blockers**: Keine
- **Questions**: Keine

### Completion Log

| Phase | Completed | Duration | Notes |
|---|---|---|---|
| Phase 1 | ✅ | - | Abschluss der grundlegenden Setup-Anpassungen. |
| Phase 2 | ⬜ | - | - |
| Phase 3 | ⬜ | - | - |
| Phase 4 | ⬜ | - | - |
| Phase 5 | ⬜ | - | - |
| Phase 6 | ⬜ | - | - |
| Phase 7 | ⬜ | - | - |

---
*This plan follows TDD, Clean Code, and architecture principles strictly.*