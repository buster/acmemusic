## Context

Die ACME Musicplayer-Anwendung ist als Modulith mit drei Modulen aufgebaut (Musicplayer, Scoreboard, Users). Die Module kommunizieren über Spring Application Events. Aktuell verwenden alle Event-Listener synchrone `@EventListener`-Annotationen, wodurch die gesamte Event-Kette in einer einzigen DB-Transaktion läuft:

```
LiedHochladenService (@Transactional)
  └── publishEvent(NeuesLiedWurdeAngelegt)
      └── ScoreBoardEventListeners.handleEvent() [sync, gleiche TX]
          └── ZaehleNeueLieder.zähleNeueAngelegteLieder() [@Transactional REQUIRED → partizipiert]
              └── publishEvent(BenutzerIstNeuerTopScorer)
                  └── UserEventListeners.handleEvent() [sync, gleiche TX]
                      └── BenutzerWurdeNeuerTopScorerService [@Transactional REQUIRED → partizipiert]
                          └── publishEvent(BenutzerHatNeueAuszeichnungErhalten)
                              └── UserEventListeners.handleEvent() [sync, SSE vor Commit!]
```

Alle Event-Listener implementieren das `EventDispatcher`-Interface aus `libs/events`, das eine generische `handleEvent(Event)`-Methode erzwingt. Die Listener verwenden `instanceof`-Checks für das Event-Dispatching.

`@EnableAsync` ist bereits in allen drei `ModuleConfiguration`-Klassen konfiguriert (`MusicplayerModuleConfiguration`, `ScoreboardModuleConfiguration`, `UsersModuleConfiguration`).

## Goals / Non-Goals

**Goals:**
- Synchrone `@EventListener` durch `@TransactionalEventListener(phase = AFTER_COMMIT)` + `@Async` ersetzen
- Generisches `handleEvent(Event)` durch typsichere Methoden pro Event-Typ ersetzen
- `EventDispatcher`-Interface entfernen (bietet mit `@TransactionalEventListener` keinen Mehrwert)
- SSE-vor-Commit-Bug beheben (wird durch `AFTER_COMMIT` automatisch gelöst)
- Integrationstests und Component-Tests auf Async-Verhalten anpassen
- Alle bestehenden Tests müssen nach der Migration grün sein

**Non-Goals:**
- Outbox-Pattern implementieren (bewusste Entscheidung — als späteres Experiment)
- Retry-Logik einführen
- Event-Reihenfolge garantieren
- `EventPublisher`-Interface oder `SpringApplicationEventPublisher` ändern
- Domain-Logik ändern (nur Adapter-Schicht wird refactored)

## Decisions

### Decision 1: @TransactionalEventListener + @Async statt Alternativen

**Gewählter Ansatz**: `@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)` + `@Async` an den Event-Listener-Methoden.

**Begründung**: Nutzt reine Spring-Boot-Bordmittel ohne neue Abhängigkeit. `@EnableAsync` ist bereits konfiguriert. Minimal-invasive Änderung, die reversibel ist.

**Alternativen betrachtet**:
- Spring Modulith (`@ApplicationModuleListener`): Robuster (Persistent Event Log, auto-Retry), aber neue Framework-Abhängigkeit. Als späterer Schritt sinnvoll
- Eigener Thread-Pool pro Modul: Unnötige Komplexität für den aktuellen Use Case
- Message Broker (Kafka/RabbitMQ): Microservice-Pattern, Overkill für einen Modulith

### Decision 2: Typsichere Methoden statt generisches Dispatching

**Gewählter Ansatz**: Jeder Event-Typ erhält eine eigene Listener-Methode mit dem konkreten Event-Typ als Parameter.

**Begründung**: `@TransactionalEventListener` dispatcht Events anhand des Parameter-Typs — kein manuelles `instanceof`-Dispatching nötig. Typsichere Methoden sind wartbarer und Spring-idiomatisch.

**Vorher** (ScoreBoardEventListeners):
```java
@EventListener
public void handleEvent(Event event) {
    if (event instanceof NeuesLiedWurdeAngelegt e) { ... }
}
```

**Nachher**:
```java
@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
@Async
public void onNeuesLiedWurdeAngelegt(NeuesLiedWurdeAngelegt event) { ... }
```

### Decision 3: EventDispatcher-Interface entfernen

**Gewählter Ansatz**: Das `EventDispatcher`-Interface in `libs/events` wird entfernt. Event-Listener-Klassen implementieren es nicht mehr.

**Begründung**: Das Interface erzwingt die Signatur `handleEvent(Event)`, die das generische Dispatching-Pattern mit `instanceof` erfordert. Mit `@TransactionalEventListener` ist diese Abstraktion kontraproduktiv — sie verhindert die typsichere Event-Bindung. `MusicPlayerEventListeners`, `ScoreBoardEventListeners` und `UserEventListeners` werden zu normalen `@Component`-Klassen mit spezifischen Listener-Methoden.

**Auswirkungen**: 
- Tests, die über `EventDispatcher`-Referenz arbeiten (z.B. `@Qualifier("scoreBoardEventListeners") EventDispatcher`), müssen angepasst werden
- Die `@ModuleApi`-Annotation am Interface entfällt — die Listener-Klassen sind keine Module-APIs sondern interne Adapter

**Alternativen betrachtet**:
- Interface behalten mit Default-Implementierung: Bietet keinen Mehrwert, verwirrt zukünftige Entwickler
- Interface zu Marker-Interface umbauen: Keine Funktion, unnötige Abstraktion

### Decision 4: Test-Strategie für Async-Events

**Gewählter Ansatz**: Integrationstests, die direkt `handleEvent()` oder `eventDispatcher.handleEvent()` aufrufen, werden umgebaut auf Event-Publishing über `ApplicationEventPublisher` mit anschließender asynchroner Verifikation.

**Begründung**: Nach der Migration gibt es kein `handleEvent()` mehr. Tests müssen den realistischen Event-Flow testen — Event publizieren, Commit abwarten, asynchrone Verarbeitung verifizieren.

**Test-Anpassungen**:
- `ScoreBoardEventListenersIntegrationTest`: Statt `eventDispatcher.handleEvent()` → `eventPublisher.publishEvent()` innerhalb einer `@Transactional`-Testmethode + Verifikation nach Commit
- `UserEventListenersIntegrationTest`: Statt `userEventListeners.handleEvent()` → Event-Publishing + Await/Polling für Mock-Verifikation
- `MusicPlayerEventListenersIntegrationTest`: Analog
- Tests für "unbekannte Events ignorieren" entfallen — typsichere Methoden empfangen keine unbekannten Events

### Decision 5: MusicPlayerEventListeners bleibt bestehen

**Gewählter Ansatz**: `MusicPlayerEventListeners` wird analog umgebaut (typsichere Methode, `@TransactionalEventListener` + `@Async`), obwohl es aktuell nur loggt.

**Begründung**: Konsistenz über alle Module. Aktuell hat der Listener bereits `@Async`, aber noch `@EventListener` statt `@TransactionalEventListener`. Der Wechsel zu `AFTER_COMMIT` stellt sicher, dass das Logging erst nach erfolgreichem Commit stattfindet.

## Risks / Trade-offs

| Risiko | Wahrscheinlichkeit | Mitigation |
|--------|-------------------|------------|
| Events gehen bei Crash verloren | Gering — Crash selten, nächster Upload korrigiert Top-Scorer | Logging + Alerting. Langfristig: Outbox-Pattern |
| Async-Tests werden flaky | Mittel — Timing-Abhängigkeiten | Awaitility oder explizites Polling mit ausreichenden Timeouts |
| ZaehleNeueLieder ist nicht idempotent | Mittel — Doppelzählung bei Retry | Idempotenz prüfen: zähleNeuesLied inkrementiert Score. Kein Dedup vorhanden — für jetzt akzeptabel, da kein Retry-Mechanismus existiert |
| EventDispatcher-Entfernung bricht ArchUnit-Tests | Gering — ArchUnit prüft Package-Abhängigkeiten, nicht Interface-Implementierung | ArchUnit-Regeln nach Entfernung verifizieren |
| Bestehende Tests brechen durch fehlende handleEvent-Methode | Hoch — alle drei Test-Klassen referenzieren handleEvent | Tests werden in der gleichen Phase umgebaut |

## Migration Plan

**Phase 1: Gherkin Feature-Test erstellen (ROT)**
1. Gherkin-Szenario schreiben, das den async Event-Flow testet (Lied hochladen → Score-Update erfolgt asynchron → Auszeichnung wird vergeben)
2. Step Definitions mit Awaitility für asynchrone Verifikation
3. Test verifiziert: HTTP-Response kommt sofort, Score-Update erfolgt nach Commit

**Phase 2: ScoreBoardEventListeners umstellen**
1. `implements EventDispatcher` entfernen
2. `handleEvent(Event)` durch `onNeuesLiedWurdeAngelegt(NeuesLiedWurdeAngelegt)` ersetzen
3. `@EventListener` durch `@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)` + `@Async` ersetzen
4. Zugehörigen Integrationstest anpassen

**Phase 3: UserEventListeners umstellen**
1. `implements EventDispatcher` entfernen
2. `handleEvent(Event)` durch drei typsichere Methoden ersetzen
3. Annotationen umstellen
4. Zugehörigen Integrationstest anpassen

**Phase 4: MusicPlayerEventListeners umstellen**
1. `implements EventDispatcher` entfernen
2. Typsichere Methode mit `@TransactionalEventListener` + `@Async`
3. Zugehörigen Integrationstest anpassen

**Phase 5: EventDispatcher-Interface entfernen**
1. `EventDispatcher.java` aus `libs/events` löschen
2. Alle Imports und Referenzen auf `EventDispatcher` aus dem gesamten Projekt entfernen
3. Compile-Verifikation

**Phase 6: Tests anpassen und verifizieren**
1. Component-Tests (Cucumber) auf Async-Verhalten prüfen und anpassen
2. E2E-Tests verifizieren
3. Vollständigen Test-Lauf durchführen

**Phase 7: Cleanup**
1. Temporäre Unit-Tests entfernen (falls vorhanden)
2. Vollständiger Build + Coverage-Check
3. Gherkin-Test GRÜN

**Rollback-Strategie**: Git-Revert. `@EventListener` + `EventDispatcher`-Interface wiederherstellen. Änderung ist vollständig reversibel.

## Open Questions

1. Ist `ZaehleNeueLieder.zähleNeuesLied()` idempotent? Falls nicht — reicht akzeptable Inkonsistenz oder muss Idempotenz hergestellt werden?
2. Sollen die Component-Tests (Cucumber) synchron bleiben (mit Fakes/Mocks) oder auf realistisches Async-Verhalten umgestellt werden?
3. Wird Awaitility als Test-Dependency benötigt oder reicht `Thread.sleep()`-basiertes Polling?
