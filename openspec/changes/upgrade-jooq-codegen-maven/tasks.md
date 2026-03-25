## 1. Abhängigkeiten aktualisieren

- [x] 1.1 Versionsnummer 3.21.0 für `org.jooq:jooq-codegen-maven` in `services/acme/pom.xml` setzen (Zeile ~394)
- [x] 1.2 Versionsnummer 3.21.0 für `org.jooq:jooq-meta` in `services/acme/pom.xml` setzen (in jooq-codegen-maven Plugin Dependencies, Zeile ~400)
- [x] 1.3 Versionsnummer 3.21.0 für `org.jooq:jooq` in `services/acme/pom.xml` setzen (Zeile ~64)
- [x] 1.4 Änderungen in pom.xml speichern und überprüfen, dass keine Syntax-Fehler vorhanden sind

## 2. Code-Regeneration

- [x] 2.1 Docker-Container für PostgreSQL starten (falls noch nicht laufen)
- [x] 2.2 `mvn clean compile` in `services/acme/` Verzeichnis ausführen, um jOOQ-Code mit neuer Version zu regenerieren
- [x] 2.3 Verifizieren, dass generierter Code in `services/acme/src/generated/java/de/acme/jooq/` erfolgreich erstellt wurde
- [x] 2.4 Diff des generierten Codes überprüfen (Änderungen in Javadoc, Methodensignaturen, etc. dokumentieren)

## 3. Tests durchführen

- [x] 3.1 Unit-Tests ausführen: `mvn test -pl services/acme`
- [x] 3.2 Verifizieren, dass alle Unit-Tests bestanden werden
- [x] 3.3 Integration-Tests ausführen (insbesondere LiedRepository, BenutzerRepository, UserScoreBoardRepository Tests)
- [x] 3.4 E2E-Tests ausführen: `mvn test -pl e2e`
- [x] 3.5 Verifizieren, dass alle E2E-Tests bestanden werden
- [x] 3.6 Code-Coverage überprüfen, dass keine neuen uncovered Lines entstanden sind

## 4. Code-Review und Commit

- [x] 4.1 Generierte Dateien im Git-Status überprüfen
- [x] 4.2 pom.xml Änderungen überprüfen (nur Versionsnummern sollten sich ändern)
- [x] 4.3 Generierte jOOQ-Klassen visuell auf offensichtliche Probleme überprüfen
- [x] 4.4 Git-Commits erstellen: `test: add failing test for jooq-codegen upgrade` (falls notwendig), dann Implementierungs-Commit, dann Refactor-Commit
- [x] 4.5 Änderungen in Repository pushen

## 5. Verifikation

- [x] 5.1 Vollständiger Maven-Build durchführen: `mvn clean verify -pl services/acme`
- [x] 5.2 Verifizieren, dass Anwendung mit `mvn spring-boot:run` startet
- [x] 5.3 Manuell in der UI einige Funktionen testen (Liedverwaltung, Benutzerregistrierung, Scoreboard)
- [x] 5.4 Logs überprüfen, dass keine Fehler oder Warnungen zum jOOQ-Code auftreten
