# language: de

Funktionalität:

  Szenario: Song hinzufügen
    Gegeben seien leere Datenbanken
    Wenn der Benutzer 'Alice' sich mit dem Passwort 'abc' und der Email 'bla@localhost.com' registriert hat
    Und der Benutzer 'Alice' den Song 'Firestarter' von 'Prodigy' hinzufügt
    Dann enthält die Datenbank 1 Lied


  Szenario: Playlist hinzufügen
    Gegeben seien folgende Songs:
    | Titel          | Interpret |
    | Firestarter    | Prodigy   |
    | Breathe        | Prodigy   |
    Und folgende Benutzer:
    | Name   |
    | Alice  |
    | Bob    |
    Wenn der Benutzer 'Alice' den Song 'Firestarter' zu einer Playlist 'Favoriten' hinzufügt
    Und der Benutzer 'Alice' den Song 'Breathe' zu einer Playlist 'Favoriten' hinzufügt
    Dann enthält die Playlist 'Favoriten' von 'Alice' die Songs:
    | Titel          | Interpret |
    | Firestarter    | Prodigy   |
    | Breathe        | Prodigy   |
