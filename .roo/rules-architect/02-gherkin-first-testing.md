# MANDATORY RULE: Gherkin-First Test Strategy

## Rule ID: ARCH-GHERKIN-001

**Priority**: MANDATORY
**Applies to**: All new feature implementations planned in Architect Mode

## Grundsatz

> Wenn neuer Code keinen messbaren Effekt nach außen hat, dann ist er entweder nicht notwendig oder muss messbar gemacht werden. Das muss **vor der Implementierung** feststehen.

Jedes neue Feature MUSS im Implementation Plan als **ersten Implementierungsschritt** einen Gherkin-basierten Akzeptanztest (`.feature`-Datei) vorsehen, der initial ROT ist. Dieser Test definiert das erwartete Business-Verhalten aus User-Perspektive.

## Pflichten des Architect

1. **Gherkin-First**: Jeder Implementation Plan MUSS als Phase 1 die Erstellung eines Gherkin Feature-Tests beinhalten
2. **Akzeptanztest vor Code**: Keine Implementierung darf geplant werden, bevor mindestens ein Gherkin-Szenario definiert ist
3. **Feature-Datei im Plan**: Der Plan MUSS den genauen Dateinamen und Speicherort der `.feature`-Datei angeben
4. **Messbarkeit**: Für jeden geplanten Code-Abschnitt muss der Architect angeben, welcher Test den äußeren Effekt misst

## Test-Hierarchie

### DEFAULT: Gherkin/BDD Tests

| Test-Typ | Einsatz | Beispiel |
|----------|---------|----------|
| Gherkin/BDD mit Simulatoren | Externe API-Interaktionen | Simulierter OpenAI/Jira/GitLab-Server |
| Gherkin/BDD als Browser-Driver | Frontend-Features | Selenium/Playwright-gesteuerte Szenarien |
| Integrationstests | Adapter-Schicht | Datenbank-Adapter mit Testcontainers |

### AUSNAHME: Dauerhafte Unit-Tests

Unit-Tests sind als **dauerhafte Tests** erlaubt, wenn **alle** folgenden Bedingungen erfüllt sind:

1. Es handelt sich um **komplexe Business-Regeln** mit kombinatorischer Komplexität
2. Die Tests sind **einfache Input/Output Tests** (reine Funktionsaufrufe)
3. Die Tests nutzen **Parametrisierung** (`@ParameterizedTest`, `pytest.mark.parametrize`) oder **Fuzzy Testing**
4. Gherkin-Tests würden durch **kombinatorische Explosion** die Testlaufzeit unverhältnismäßig verlangsamen

**Beispiel**: Eine Preisberechnungs-Engine mit 50+ Regelkombinationen → Parametrisierte Unit-Tests für die Berechnungslogik, Gherkin-Test für den End-to-End-Flow.

### TEMPORÄR: Implementierungshilfe-Unit-Tests

Unit-Tests für interne Implementierungsdetails sind **ausschließlich temporär** erlaubt:

- DÜRFEN im Plan vorgesehen werden, wenn sie die Implementierung erleichtern
- MÜSSEN explizit als `[TEMP]` im Plan gekennzeichnet sein
- MÜSSEN in der finalen Phase des Plans **entfernt** werden
- Die durch sie abgedeckte Coverage MUSS durch Gherkin-Tests oder Integrationstests ersetzt werden

### VERBOTEN als dauerhafte Tests

- ❌ Unit-Tests für interne Implementierungsdetails ohne messbaren äußeren Effekt
- ❌ Unit-Tests die nur für Code-Coverage-Prozentsatz existieren
- ❌ Mock-Heavy Tests für internes Wiring
- ❌ Tests die bei Implementation-Change brechen, aber nicht bei Behavior-Change

## Entscheidungsbaum für den Architect

```
Neues Feature / neuer Code geplant
  │
  ├─ Hat messbaren äußeren Effekt?
  │    ├─ JA → Gherkin-Test definieren (DEFAULT)
  │    │         │
  │    │         ├─ Kombinatorisch komplex?
  │    │         │    ├─ JA → Zusätzlich parametrisierte Unit-Tests erlaubt
  │    │         │    └─ NEIN → Gherkin-Test reicht
  │    │         │
  │    │         └─ Adapter/Infrastruktur?
  │    │              ├─ JA → Integrationstest (z.B. Testcontainers)
  │    │              └─ NEIN → Gherkin-Test
  │    │
  │    └─ NEIN → Code ist nicht notwendig ODER Effekt muss messbar gemacht werden
  │
  └─ Braucht Implementierungshilfe?
       ├─ JA → [TEMP] Unit-Test planen (wird am Ende entfernt)
       └─ NEIN → Keine Unit-Tests
```

## Implementation Plan Pflicht-Phasen

```
Phase 1: Gherkin Feature Test erstellen (ROT)
         - .feature-Datei mit Szenarien
         - Step Definitions (Grundgerüst)
         - Simulatoren für externe APIs falls nötig
Phase 2-N: Implementierung
         - Optional: [TEMP] Unit-Tests als Implementierungshilfe
         - Optional: Parametrisierte Unit-Tests für kombinatorisch komplexe Business-Regeln
Phase N+1: Cleanup
         - Alle [TEMP] Unit-Tests entfernen
         - Coverage-Verifikation: Nur Gherkin/Integration/erlaubte Unit-Tests
Phase N+2: Finaler Test-Lauf
         - Gherkin-Test GRÜN
         - Integrationstests GRÜN
         - Parametrisierte Business-Rule-Tests GRÜN (falls vorhanden)
         - Coverage-Check bestanden
```

## Litmus Tests

### Für jeden geplanten Test:

> "Bricht dieser Test beim Business-Behavior Change, oder nur beim Implementation Change?"
> - Bricht bei Behavior Change → ✅ Test behalten
> - Bricht nur bei Implementation Change → ❌ Test entfernen oder als [TEMP] markieren

### Für jeden geplanten Code-Abschnitt:

> "Welcher Test misst den äußeren Effekt dieses Codes?"
> - Antwort vorhanden → ✅ Code darf implementiert werden
> - Keine Antwort → ❌ Code ist nicht notwendig oder Effekt muss messbar gemacht werden

### Für jeden Unit-Test im Plan:

> "Warum ist dies kein Gherkin-Test?"
> - Kombinatorische Explosion → ✅ Erlaubt als parametrisierter Unit-Test
> - Hilft bei Implementierung → ✅ Erlaubt als [TEMP]
> - Andere Begründung → ❌ Muss Gherkin-Test werden

## Enforcement

- Implementation Plan ohne Gherkin-Test als Phase 1 = **ABLEHNUNG** durch Orchestrator
- Dauerhafte Unit-Tests für Implementierungsdetails im Plan = **PLAN-REVISION** erforderlich
- Coverage durch interne Unit-Tests am Ende = **PLAN-VIOLATION**
- Nicht entfernte [TEMP] Tests = **PLAN-VIOLATION**

## Abgrenzung zu bestehenden Regeln

- Ergänzt [`ARCH-PLAN-001`](.roo/rules-architect/00-planning-only.md) um Test-Strategie-Vorgaben
- Konsistent mit [`COV-CLEAN-001`](.roo/rules-code/02-coverage-and-cleanup.md) — gleiche verbotene Test-Typen
- TDD-Zyklus RED-GREEN-REFACTOR im Code-Mode bleibt bestehen, aber der initiale RED-Test MUSS ein Gherkin-Test sein
- Parametrisierte Unit-Tests für Business-Regeln ergänzen (nicht ersetzen) die Gherkin-Tests
