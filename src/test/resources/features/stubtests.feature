# language: de

Funktionalität:

  @T2T
  @T2R
  @R2R
  Szenario: Registrierung
#    Gegeben sei eine leere Datenbank
    Und folgende Benutzer:
      | Name | Passwort | Email          |
      | John{UUID} | abc      | john{UUID}@localhost |
    Wenn der Benutzer 'Alice{UUID}' sich mit dem Passwort 'abc' und der Email 'alice{UUID}@localhost' registriert hat
    Dann kennt der Service 2 Benutzer

  @T2T
  @T2R
  Szenario: Song hinzufügen, Playlist erstellen, Song hinzufügen und Playlist abspielen
#    Gegeben sei eine leere Datenbank
    Und folgende Songs:
      | Titel          | Dateiname                    |
      | Epic Song{UUID}      | BoxCat Games - Epic Song.mp3 |
      | Breaking Rules{UUID} | Serat - Breaking Rules.mp3   |
    Und folgende Benutzer:
      | Name  | Passwort | Email             |
      | Alice{UUID} | abc      | bla{UUID}@localhost.com |
    Wenn der Benutzer 'Alice{UUID}' die Playlist 'Favoriten{UUID}' erstellt
    Und der Benutzer 'Alice{UUID}' das Lied 'Epic Song{UUID}' zur Playlist 'Favoriten{UUID}' hinzufügt
    Und der Benutzer 'Alice{UUID}' das Lied 'Breaking Rules{UUID}' zur Playlist 'Favoriten{UUID}' hinzufügt
#    Und der Benutzer 'Alice' das Lied 'Firestarter' zur Playlist 'Favoriten' hinzufügt
    Dann enthält die Playlist 'Favoriten{UUID}' von 'Alice{UUID}' 2 Lieder

  @T2T
  @T2R
  Szenario: Song abspielen
#    Gegeben sei eine leere Datenbank
    Und folgende Songs:
      | Titel     | Dateiname                    |
      | Epic Song{UUID} | BoxCat Games - Epic Song.mp3 |
    Und folgende Benutzer:
      | Name  | Passwort | Email             |
      | Alice{UUID} | abc      | bla{UUID}@localhost.com |
    Wenn der Benutzer 'Alice{UUID}' das Lied 'Epic Song{UUID}' abspielt
    Dann erhält der Benutzer den Song 'Epic Song{UUID}' mit mehr als 1000000 Byte Größe

  @T2R
  Szenario: Wahnsinnig Datenbankintensives Song hinzufügen
#    Gegeben sei eine leere Datenbank
    Und folgende Songs:
      | Titel          | Dateiname                    |
      | Epic Song{UUID}      | BoxCat Games - Epic Song.mp3 |
      | Breaking Rules{UUID} | Serat - Breaking Rules.mp3   |
    Und folgende Benutzer:
      | Name  | Passwort | Email             |
      | Alice{UUID} | abc      | bla{UUID}@localhost.com |
    Wenn der Benutzer 'Alice{UUID}' die Playlist 'Favoriten{UUID}' erstellt
    Und der Benutzer 'Alice{UUID}' das Lied 'Epic Song{UUID}' zur Playlist 'Favoriten{UUID}' hinzufügt
    Und der Benutzer 'Alice{UUID}' das Lied 'Breaking Rules{UUID}' zur Playlist 'Favoriten{UUID}' hinzufügt
#    Und der Benutzer 'Alice' das Lied 'Firestarter' zur Playlist 'Favoriten' hinzufügt
    Dann enthält die Playlist 'Favoriten{UUID}' von 'Alice{UUID}' 2 Lieder
