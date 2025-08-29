## **Rules für KI-Agenten**

### 🎯 **1. Initialisierung & Kontinuität**

**Bei Story-Start:**

1. Research Plan aus Template initialisieren [^1]
2. Erste Discovery-Session durchführen (min. 30 Minuten)
3. Initial Knowledge Base befüllen
4. Erste Hypothesen formulieren

**Kontinuierliche Updates:**

- **Nach jedem bedeutsamen Finding**: Knowledge Base aktualisieren
- **Alle 2 Stunden aktive Arbeit**: Status-Update im Plan
- **Bei Blockern**: Sofort dokumentieren mit Lösungsansatz

### 🔍 **2. Iterative Tiefenanalyse**

```markdown
WHILE (Erfolgskriterien nicht erfüllt) DO:
1. Identifiziere nächsten kritischen Untersuchungsbereich
2. Formuliere spezifische Untersuchungsfragen
3. Führe gezielte Analyse durch:
- Code-Review mit konkreten Metriken
- Dokumentationsanalyse mit Quellenangaben
- Externe Recherche mit Validierung
4. Dokumentiere Findings mit:
- Evidenz (Screenshots, Code-Snippets, Links)
- Impact-Assessment
5. Aktualisiere Living Knowledge Base
6. Prüfe ob neue Erkenntnisse Scope-Anpassung erfordern
END
```

### 📝 **3. Dokumentationsstandards**

**Jedes Finding muss enthalten:**

- **Was**: Präzise Beschreibung
- **Wo**: Exakte Lokation (File, Line, System)
- **Warum relevant**: Business/Technical Impact
- **Evidenz**: Screenshots, Code, Logs
- **Next Steps**: Konkrete Folgeaktionen

**Diagramm-Erstellung:**

```markdown
Für jede Analyse kann erstellt werden:

- 1x System Context Diagram (C4 Level 1)
- 1x Component Diagram (C4 Level 2)
- 1x Process Flow (BPMN oder Sequenz)
- Optional: Data Flow, State Machines, ER-Diagrams
```

### 🔄 **4. Phasen-Flexibilität**

**Dynamische Phasengestaltung:** [^2]

- Phasen können **parallel** laufen
- Phasen können **wiederholt** werden
- Neue Phasen können **eingefügt** werden
- Phasen können **übersprungen** werden (mit Begründung)

**Trigger für Phasenwechsel:**

- Alle Must-Have-Kriterien der Phase erfüllt
- Neue kritische Erkenntnisse erfordern anderen Fokus
- Externe Abhängigkeiten blockieren Fortschritt

### 📊 **5. Finale Deliverables**

**Pflicht-Bestandteile des Analysedokuments:**

1. **Executive Summary** (1 Seite)
2. **Detaillierte Analyse** mit:
    - Fachlicher Kontext
    - Technische Bewertung
    - Detaillierte Code-Analysen
    - Risikobewertung
3. **Visualisierungen**:
    - Aussagekräftige Diagramme
    - Annotationen und Erklärungen
4. **Handlungsempfehlungen**:
    - Priorisierte Maßnahmen
    - Aufwandsschätzungen
5. **Anhänge**:
    - Referenzen

### ⚡ **7. Effizienz-Regeln**

**Priorisierung:**

```markdown
Fokus-Reihenfolge:

1. Business-kritische Aspekte
2. Technische Blocker
3. Quick Wins (Aufwand < 1 Tag)
4. Nice-to-have Optimierungen
```

### 🚫 **8. Non-Compliance Konsequenzen**

- **Ohne Research Plan gestartet**: Sofort stoppen, Plan erstellen
- **Findings ohne Evidenz**: Zurück zur Analyse-Phase
- **Keine Diagramme erstellt**: Visualisierung vor Abschluss nachholen
- **Knowledge Base nicht aktuell**: Blocked-Status setzen, Update durchführen
