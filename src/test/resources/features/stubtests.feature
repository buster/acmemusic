# language: de

Funktionalität:

  @T2T
  @T2R
  Szenario: Registrierung
    Gegeben sei eine leere Datenbank
    Und folgende Benutzer:
      | Name | Passwort | Email          |
      | John | abc      | john@localhost |
    Wenn der Benutzer 'Alice' sich mit dem Passwort 'abc' und der Email 'alice@localhost' registriert hat
    Dann kennt der Service 2 Benutzer

  @T2T
  @T2R
  Szenario: Song hinzufügen
    Gegeben sei eine leere Datenbank
    Und folgende Songs:
      | Titel       |
      | Firestarter |
      | Breathe     |
    Und folgende Benutzer:
      | Name  | Passwort | Email             |
      | Alice | abc      | bla@localhost.com |
    Wenn der Benutzer 'Alice' die Playlist 'Favoriten' erstellt
    Und der Benutzer 'Alice' das Lied 'Firestarter' zur Playlist 'Favoriten' hinzufügt
    Und der Benutzer 'Alice' das Lied 'Breathe' zur Playlist 'Favoriten' hinzufügt
#    Und der Benutzer 'Alice' das Lied 'Firestarter' zur Playlist 'Favoriten' hinzufügt
    Dann enthält die Playlist 'Favoriten' von 'Alice' 2 Lieder

  @T2R
  Szenario: Wahnsinnig Datenbankintensives Song hinzufügen
    Gegeben sei eine leere Datenbank
    Und folgende Songs:
      | Titel       |
      | Firestarter |
      | Breathe     |
    Und folgende Benutzer:
      | Name  | Passwort | Email             |
      | Alice | abc      | bla@localhost.com |
    Wenn der Benutzer 'Alice' die Playlist 'Favoriten' erstellt
    Und der Benutzer 'Alice' das Lied 'Firestarter' zur Playlist 'Favoriten' hinzufügt
    Und der Benutzer 'Alice' das Lied 'Breathe' zur Playlist 'Favoriten' hinzufügt
#    Und der Benutzer 'Alice' das Lied 'Firestarter' zur Playlist 'Favoriten' hinzufügt
    Dann enthält die Playlist 'Favoriten' von 'Alice' 2 Lieder
