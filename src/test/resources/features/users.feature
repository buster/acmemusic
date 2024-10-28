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
    Wenn der Benutzer 'Alice' sich mit dem Passwort 'abc' und der Email 'alice@localhost' registriert hat
    Dann kennt der Service 2 Benutzer
