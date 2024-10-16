# language: de

Funktionalität:

  @T2T
  @T2R
  @R2R
  Szenario: Registrierung
#    Gegeben sei eine leere Datenbank
    Und folgende Benutzer:
      | Name | Passwort | Email          |
      | John | abc      | john@localhost |
    Wenn der Benutzer 'Alice' sich mit dem Passwort 'abc' und der Email 'alice@localhost' registriert hat
    Und sich der Benutzer 'Alice' mit dem Passwort 'abc' und der Email 'alice@localhost' eingelogged hat
    Dann kennt der Service 2 Benutzer

  @T2T
  @T2R
  Szenario: Song hinzufügen, Playlist erstellen, Song hinzufügen und Playlist abspielen
#    Gegeben sei eine leere Datenbank
    Gegeben seien folgende Benutzer:
      | Name  | Passwort | Email             |
      | Alice | abc      | bla@localhost.com |
    Und folgende Songs:
      | Titel          | Dateiname                    | Benutzer |
      | Epic Song      | BoxCat Games - Epic Song.mp3 | Alice    |
      | Breaking Rules | Serat - Breaking Rules.mp3   | Alice    |
    Wenn der Benutzer 'Alice' die Playlist 'Favoriten' erstellt
    Und der Benutzer 'Alice' das Lied 'Epic Song' zur Playlist 'Favoriten' hinzufügt
    Und der Benutzer 'Alice' das Lied 'Breaking Rules' zur Playlist 'Favoriten' hinzufügt
#    Und der Benutzer 'Alice' das Lied 'Firestarter' zur Playlist 'Favoriten' hinzufügt
    Dann enthält die Playlist 'Favoriten' von 'Alice' 2 Lieder

  @T2T
  @T2R
  Szenario: Song abspielen
#    Gegeben sei eine leere Datenbank
    Gegeben seien folgende Benutzer:
      | Name  | Passwort | Email             |
      | Alice | abc      | bla@localhost.com |
    Und folgende Songs:
      | Titel     | Dateiname                    | Benutzer |
      | Epic Song | BoxCat Games - Epic Song.mp3 | Alice    |
    Wenn der Benutzer 'Alice' das Lied 'Epic Song' abspielt
    Dann erhält der Benutzer den Song 'Epic Song' mit mehr als 1000000 Byte Größe

  @T2R
  Szenario: Wahnsinnig Datenbankintensives Song hinzufügen
#    Gegeben sei eine leere Datenbank
    Gegeben seien folgende Benutzer:
      | Name  | Passwort | Email             |
      | Alice | abc      | bla@localhost.com |
    Und folgende Songs:
      | Titel          | Dateiname                    | Benutzer |
      | Epic Song      | BoxCat Games - Epic Song.mp3 | Alice    |
      | Breaking Rules | Serat - Breaking Rules.mp3   | Alice    |

    Wenn der Benutzer 'Alice' die Playlist 'Favoriten' erstellt
    Und der Benutzer 'Alice' das Lied 'Epic Song' zur Playlist 'Favoriten' hinzufügt
    Und der Benutzer 'Alice' das Lied 'Breaking Rules' zur Playlist 'Favoriten' hinzufügt
#    Und der Benutzer 'Alice' das Lied 'Firestarter' zur Playlist 'Favoriten' hinzufügt
    Dann enthält die Playlist 'Favoriten' von 'Alice' 2 Lieder
