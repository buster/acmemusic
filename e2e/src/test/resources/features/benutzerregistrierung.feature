# language: de
@E2E
Funktionalität: Benutzerregistrierung

  Szenario: Ein Benutzer registriert sich erfolgreich
  Gegebenseien folgende Benutzer:
  | Name      | Passwort   | Email                |
  | MaxMuster | geheim123  | max@acme.de          |
    Wenn der Benutzer "MaxMuster" sich mit dem Passwort "geheim123" und der Email "max@acme.de" registriert hat