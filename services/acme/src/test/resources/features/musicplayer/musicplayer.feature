# language: de

Funktionalität:

  @T2T
  @T2R
  @R2R
  Szenario: Song abspielen
    Gegeben seien folgende Benutzer:
      | Name  | Passwort | Email             |
      | Alice | abc      | bla@localhost.com |
    Und folgende Songs:
      | Titel     | Dateiname                          | Benutzer |
      | Epic Song | files/BoxCat Games - Epic Song.mp3 | Alice    |
    Wenn der Benutzer 'Alice' das Lied 'Epic Song' abspielt
    Dann erhält der Benutzer den Song 'Epic Song' mit mehr als 10 Sekunden Länge
