## 1. Gherkin Feature-Test erstellen (ROT)

- [ ] 1.1 Gherkin-Feature-Datei `services/acme/src/test/resources/features/scoreboard/async-event-processing.feature` erstellen mit Szenarien:
  - Szenario: Lied-Upload löst asynchrones Score-Update aus
  - Szenario: SSE-Benachrichtigung erfolgt erst nach DB-Commit
  - Szenario: Fehler im Scoreboard verhindert nicht den Lied-Upload
- [ ] 1.2 Step Definitions Grundgerüst erstellen mit Awaitility für asynchrone Verifikation
- [ ] 1.3 Awaitility als Test-Dependency in `services/acme/pom.xml` hinzufügen (falls noch nicht vorhanden)
- [ ] 1.4 Verifizieren, dass der Gherkin-Test kompiliert und ROT ist (Szenarien schlagen fehl)

## 2. ScoreBoardEventListeners umstellen

- [ ] 2.1 `implements EventDispatcher` aus `ScoreBoardEventListeners` entfernen
- [ ] 2.2 `handleEvent(Event)` durch typsichere Methode `onNeuesLiedWurdeAngelegt(NeuesLiedWurdeAngelegt)` ersetzen
- [ ] 2.3 `@EventListener` durch `@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)` + `@Async` ersetzen
- [ ] 2.4 Unnötige Imports entfernen (`Event`, `EventDispatcher`)
- [ ] 2.5 `ScoreBoardEventListenersIntegrationTest` anpassen:
  - `EventDispatcher`-Referenz durch direkte `ScoreBoardEventListeners`-Referenz ersetzen
  - `handleEvent()`-Aufrufe durch `ApplicationEventPublisher.publishEvent()` ersetzen
  - Asynchrone Verifikation hinzufügen (Awaitility oder Polling)
  - Test für "unbekannte Events ignorieren" entfernen (nicht mehr relevant)
- [ ] 2.6 Kompilieren und Tests ausführen: `mvn test -pl services/acme -Dtest=ScoreBoardEventListenersIntegrationTest`

## 3. UserEventListeners umstellen

- [ ] 3.1 `implements EventDispatcher` aus `UserEventListeners` entfernen
- [ ] 3.2 `handleEvent(Event)` durch drei typsichere Methoden ersetzen:
  - `onBenutzerIstNeuerTopScorer(BenutzerIstNeuerTopScorer)`
  - `onBenutzerHatNeueAuszeichnung(BenutzerHatNeueAuszeichnungErhalten)`
  - `onBenutzerHatAuszeichnungVerloren(BenutzerHatAuszeichnungAnAnderenNutzerVerloren)`
- [ ] 3.3 Alle drei Methoden mit `@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)` + `@Async` annotieren
- [ ] 3.4 Unnötige Imports entfernen (`Event`, `EventDispatcher`)
- [ ] 3.5 `UserEventListenersIntegrationTest` anpassen:
  - `handleEvent()`-Aufrufe durch direkte Methodenaufrufe oder Event-Publishing ersetzen
  - Asynchrone Verifikation für Mock-Verifizierung hinzufügen
  - Test für "unbekannte Events ignorieren" entfernen
- [ ] 3.6 Kompilieren und Tests ausführen: `mvn test -pl services/acme -Dtest=UserEventListenersIntegrationTest`

## 4. MusicPlayerEventListeners umstellen

- [ ] 4.1 `implements EventDispatcher` aus `MusicPlayerEventListeners` entfernen
- [ ] 4.2 `handleEvent(Event)` durch typsichere Methode ersetzen (z.B. `onNeuesLiedWurdeAngelegt(NeuesLiedWurdeAngelegt)` oder allgemeinere Log-Methode)
- [ ] 4.3 `@EventListener` durch `@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)` ersetzen (behält `@Async`)
- [ ] 4.4 Unnötige Imports entfernen (`Event`, `EventDispatcher`)
- [ ] 4.5 `MusicPlayerEventListenersIntegrationTest` anpassen:
  - `EventDispatcher`-Referenz durch direkte `MusicPlayerEventListeners`-Referenz ersetzen
  - Tests auf neue Methoden-Signatur umstellen
- [ ] 4.6 Kompilieren und Tests ausführen: `mvn test -pl services/acme -Dtest=MusicPlayerEventListenersIntegrationTest`

## 5. EventDispatcher-Interface entfernen

- [ ] 5.1 `libs/events/src/main/java/de/acme/musicplayer/common/events/EventDispatcher.java` löschen
- [ ] 5.2 Alle verbleibenden Imports und Referenzen auf `EventDispatcher` im gesamten Projekt suchen und entfernen
- [ ] 5.3 Prüfen ob ArchUnit-Tests (`ModularizationArchTest`, etc.) auf `EventDispatcher` referenzieren und ggf. anpassen
- [ ] 5.4 Kompilieren: `mvn compile -pl libs/events,services/acme`
- [ ] 5.5 Vollständigen Test-Lauf: `mvn test -pl services/acme`

## 6. Component-Tests und E2E-Tests anpassen

- [ ] 6.1 Component-Tests prüfen: `ScoreboardSteps`, `SongSteps`, `UserSteps` — ggf. Async-Await hinzufügen
- [ ] 6.2 Component-Test-Konfigurationen prüfen (`ScoreboardCucumberConfiguration`, `MusicplayerCucumberConfiguration`, `UsersCucumberConfiguration`)
- [ ] 6.3 Feature-Dateien (`musicplayer.feature`, `scoreboard.feature`, `users.feature`) auf Anpassungsbedarf prüfen
- [ ] 6.4 Component-Tests ausführen: `mvn test -pl services/acme`
- [ ] 6.5 E2E-Tests ausführen: `mvn test -pl e2e`

## 7. Idempotenz-Prüfung

- [ ] 7.1 `ZaehleNeueLieder.zähleNeueAngelegteLieder()` auf Idempotenz analysieren — kann `zähleNeuesLied` doppelt zählen?
- [ ] 7.2 Falls nicht idempotent: Dokumentieren als bekannte Einschränkung (kein Retry vorhanden, daher akzeptabel)
- [ ] 7.3 `BenutzerWurdeNeuerTopScorerService.vergebeAuszeichnungFürNeuenTopScorer()` auf Idempotenz analysieren (Set-Semantik bei Auszeichnungen)

## 8. Cleanup und Verifikation

- [ ] 8.1 Alle [TEMP] Unit-Tests entfernen (falls vorhanden)
- [ ] 8.2 Vollständiger Maven-Build: `mvn clean verify -pl services/acme`
- [ ] 8.3 Gherkin-Test aus Phase 1 GRÜN verifizieren
- [ ] 8.4 Code-Coverage prüfen: keine neuen uncovered Lines durch die Migration
- [ ] 8.5 Git-Commits erstellen gemäß TDD-Phasen (RED → GREEN → REFACTOR)
- [ ] 8.6 Finale Verifikation: Anwendung starten und manuell SSE-Benachrichtigung nach Lied-Upload testen
