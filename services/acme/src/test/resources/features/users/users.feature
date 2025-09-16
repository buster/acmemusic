# language: de

Funktionalität:

  Szenario: Registrierung
    Gegeben seien  folgende Benutzer:
      | Name | Passwort | Email          |
      | John | abc      | john@localhost |
    Wenn der Benutzer 'Alice' sich mit dem Passwort 'abc' und der Email 'john@localhost' registriert und angemeldet hat
    Dann kennt der Service 2 Benutzer

  Szenario: Registrierung
    Gegeben seien  folgende Benutzer:
      | Name | Passwort | Email          |
      | John | abc      | john@localhost |
      | Bob  | abc      | bob@localhost  |
    Wenn der Benutzer 'John' den Benutzer 'Bob' als TopScorer abgelöst hat
    Dann erhält der Benutzer 'John' die Auszeichnung 'MUSIC_LOVER_LOVER'
    Und der Benutzer 'Bob' erhält nicht die Auszeichnung 'MUSIC_LOVER_LOVER'
    Wenn der Benutzer 'Bob' den Benutzer 'John' als TopScorer abgelöst hat
    Dann erhält der Benutzer 'Bob' die Auszeichnung 'MUSIC_LOVER_LOVER'

  Szenario: Registrierung mit leerem Passwort
    Wenn der Benutzer 'Alice' sich mit dem Passwort '' und der Email 'alice@localhost' registriert und angemeldet hat
    Dann schlägt die Registrierung fehl mit der Fehlermeldung 'Passwort darf nicht leer sein'
