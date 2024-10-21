# language: de

Funktionalität:

  @T2T
  @T2R
  Szenario: Auszeichnungen
#    Gegeben sei eine leere Datenbank
    Gegeben seien folgende Benutzer:
      | Name  | Passwort | Email         |
      | Alice | abc      | bla@localhost |
      | Bob   | 123      | bob@localhost |
    Wenn der Benutzer 'Alice' lädt das Lied mit dem Titel 'Epic Song 1' aus der Datei 'BoxCat Games - Epic Song.mp3' hoch
#    Und folgende Songs:
#      | Titel     | Dateiname                    | Benutzer |
#      | Epic Song | BoxCat Games - Epic Song.mp3 | Alice    |
#    Dann erhält der Benutzer 'Alice' die Auszeichnung 'MUSIC_LOVER_LOVER'
#    Und der Benutzer 'Bob' erhält die Auszeichnung 'LAME_DUCK'
#    Wenn der Benutzer 'Alice' das Lied 'Epic Song 1' abspielt
#    Und der Benutzer 'Bob' das Lied 'Epic Song 1' abspielt
#    Dann erhält das Lied 'Epic Song 1' die Auszeichnung 'TOP_SONG'
