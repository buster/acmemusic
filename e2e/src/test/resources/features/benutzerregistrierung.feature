# language: de
@E2E
Funktionalität: Benutzerregistrierung

  Grundlage:
    Angenommen eine leere Datenbank

  Szenario: Ein Benutzer registriert sich erfolgreich und spielt Lied ab
    Wenn der Benutzer "MaxMuster" sich mit dem Passwort "geheim123" und der Email "max@acme.de" registriert und angemeldet hat
    Und der Benutzer die MP3-Datei "BoxCat Games - Epic Song.mp3" hochlädt
    Dann kennt der Service 1 Benutzer
    Und der Benutzer das letzte hochgeladene Lied erfolgreich abspielt

  Szenario: Benutzer verliert Auszeichnung an anderen Benutzer und erhält SSE-Event
    Wenn der Benutzer "AliceMusic" sich mit dem Passwort "geheim123" und der Email "alice@acme.de" registriert und angemeldet hat
    Wenn der Benutzer "AliceMusic" die MP3-Datei "BoxCat Games - Epic Song.mp3" hochlädt
    Dann sollte der Benutzer ein Popup mit dem Inhalt "MUSIC_LOVER_LOVER erhalten" sehen

    Und der Benutzer "BobRocker" sich mit dem Passwort "geheim456" und der Email "bob@acme.de" registriert und angemeldet hat
    Und der Benutzer "BobRocker" die MP3-Datei "Serat - Breaking Rules.mp3" hochlädt
    Und der Benutzer "BobRocker" die MP3-Datei "BoxCat Games - Epic Song.mp3" hochlädt

    Dann sollte der Benutzer ein Popup mit dem Inhalt "Benutzer BobRocker hat die Auszeichnung MUSIC_LOVER_LOVER erhalten" sehen

