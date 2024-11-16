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

