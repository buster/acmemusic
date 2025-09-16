# language: de

Funktionalität:

  @T2T
  Szenario: Auszeichnungen
    Gegeben seien folgende Benutzer:
      | Name  | Passwort | Email         |
      | Alice | abc      | bla@localhost |
      | Bob   | 123      | bob@localhost |
    Wenn der Benutzer 'Bob' ein neues Lied namens 'Epic Song 1' aus der Datei 'files/BoxCat Games - Epic Song.mp3' hochgeladen hat
    Dann ist der Benutzer 'Bob' neuer TopScorer geworden
    Wenn der Benutzer 'Alice' ein neues Lied namens 'Epic Song 1' aus der Datei 'files/BoxCat Games - Epic Song.mp3' hochgeladen hat
    Und der Benutzer 'Alice' ein neues Lied namens 'Epic Song 1' aus der Datei 'files/BoxCat Games - Epic Song.mp3' hochgeladen hat
    Dann ist der Benutzer 'Alice' neuer TopScorer geworden und hat 'Bob' abgelöst
