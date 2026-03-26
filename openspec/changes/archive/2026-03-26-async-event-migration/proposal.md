## Warum

Die aktuelle Event-Verarbeitung im ACME Modulith nutzt synchrone `@EventListener`, wodurch die gesamte Event-Kette (Musicplayer → Scoreboard → Users → SSE) in einer einzigen DB-Transaktion abläuft. Das verursacht drei konkrete Probleme:

1. **Fehler-Propagation**: Ein Fehler im Scoreboard-Modul (z.B. DB-Timeout) verhindert den Lied-Upload — obwohl das Scoreboard für den Upload-Vorgang nicht kritisch ist
2. **SSE-vor-Commit-Bug**: SSE-Benachrichtigungen an den Client werden gesendet, bevor die Transaktion committed wurde. Bei einem Rollback hat der Client eine falsche Benachrichtigung erhalten
3. **Latenz**: Die HTTP-Response blockiert, bis die gesamte Event-Kette durchlaufen ist (Lied-Insert → Score-Update → Auszeichnung-Update → SSE-Send)

## Was ändert sich

- **Event-Listener-Annotationen**: `@EventListener` wird durch `@TransactionalEventListener(phase = AFTER_COMMIT)` + `@Async` ersetzt
- **Typsichere Event-Methoden**: Generisches `handleEvent(Event)` mit `instanceof`-Dispatching wird durch separate, typsichere Methoden pro Event-Typ ersetzt
- **EventDispatcher-Interface entfernen**: Das Interface in `libs/events` erzwingt generisches Dispatching und bietet mit `@TransactionalEventListener` keinen Mehrwert mehr
- **Transaktionsgrenzen**: Jedes Modul erhält eine eigene Transaktion statt einer gemeinsamen modul-übergreifenden Transaktion
- **Tests anpassen**: Async-Tests erfordern Polling/Awaiting statt deterministischer Reihenfolge

## Fähigkeiten

### Neue Fähigkeiten

Entfällt — dies ist ein Refactoring der bestehenden Event-Verarbeitung.

### Geänderte Fähigkeiten

- `event-processing`: Umstellung von synchroner auf asynchrone Event-Verarbeitung mit `@TransactionalEventListener(phase = AFTER_COMMIT)` + `@Async`

## Auswirkungen

- **Betroffener Code**:
  - `ScoreBoardEventListeners.java` — Annotation-Wechsel, typsichere Methode
  - `UserEventListeners.java` — Annotation-Wechsel, drei typsichere Methoden
  - `MusicPlayerEventListeners.java` — Annotation-Wechsel, typsichere Methode
  - `EventDispatcher.java` — Interface entfernen
  - Zugehörige Integrationstests (3 Test-Klassen)

- **Abhängigkeiten**: Keine neuen Abhängigkeiten. `@EnableAsync` ist bereits in allen drei `ModuleConfiguration`-Klassen vorhanden

- **Tests**: Integrationstests müssen von synchroner auf asynchrone Verifikation umgestellt werden. Bestehende Component-Tests und E2E-Tests müssen auf Async-Verhalten angepasst werden

- **Bekannte Einschränkungen**:
  - Events gehen bei Crash verloren (kein Outbox — bewusste Entscheidung)
  - Keine automatische Retry-Logik
  - Event-Reihenfolge nicht garantiert
