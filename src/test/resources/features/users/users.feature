# language: de

Funktionalität:

  @T2T
  @T2R
  @R2R
  @Users
  Szenario: Registrierung
    Gegeben seien  folgende Benutzer:
      | Name | Passwort | Email          |
      | John | abc      | john@localhost |
    Wenn der Benutzer 'Alice' sich mit dem Passwort 'abc' und der Email 'John@localhost' registriert hat
    Dann kennt der Service 2 Benutzer

  @T2T
#  @T2R
#  @R2R
  @Users
  Szenario: Registrierung
    Gegeben seien  folgende Benutzer:
      | Name | Passwort | Email          |
      | John | abc      | john@localhost |
      | Bob  | abc      | bob@localhost  |
    Wenn das Ereignis 'BenutzerIstNeuerTopScorer' mit den folgenden Werten empfangen wird:
      | "neuerTopScorer" | {"Id": "<BenutzerId: John>"} |
      | "alterTopScorer" | {"Id": "<BenutzerId: Bob>"}  |
      | "tenantId"       | {"value": "<tenantId>"}      |
    Dann erhält der Benutzer 'John' die Auszeichnung 'MUSIC_LOVER_LOVER'
    Und der Benutzer 'Bob' erhält nicht die Auszeichnung 'MUSIC_LOVER_LOVER'
    Wenn das Ereignis 'BenutzerIstNeuerTopScorer' mit den folgenden Werten empfangen wird:
      | "neuerTopScorer" | {"Id": "<BenutzerId: Bob>"}  |
      | "alterTopScorer" | {"Id": "<BenutzerId: John>"} |
      | "tenantId"       | {"value": "<tenantId>"}      |
    Dann erhält der Benutzer 'Bob' die Auszeichnung 'MUSIC_LOVER_LOVER'
