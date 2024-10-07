# language: de

Funktionalität:

  Szenario: Registrierung
    Gegeben seien folgende Benutzer:
      | Name   | Passwort | Email              |
      | Alice2 | abc      | bla2@localhost.com |
    Wenn der Benutzer 'Alice' sich mit dem Passwort 'abc' und der Email 'bla@localhost.com' registriert hat
    Dann kennt der Service 2 Benutzer

  Szenario: Song hinzufügen
    Gegeben seien folgende Songs:
      | Titel       | Interpret | Album           | Genre  | Erscheinungsjahr | URI                                        |
      | Firestarter | Prodigy   | Fat of the Land | Techno | 1996             | http://www.youtube.com/watch?v=wmin5WkOuPw |
      | Breathe     | Prodigy   | Fat of the Land | Techno | 1996             | http://www.youtube.com/watch?v=6_PAHbqq-o4 |
    Und folgende Benutzer:
        | Name   | Passwort | Email              |
        | Alice  | abc      | bla@localhost.com  |
    Wenn der Benutzer 'Alice' das Lied 'Firestarter' zur Playlist 'Favoriten' hinzufügt
    Und der Benutzer 'Alice' das Lied 'Breathe' zur Playlist 'Favoriten' hinzufügt
    Und der Benutzer 'Alice' das Lied 'Firestarter' zur Playlist 'Favoriten' hinzufügt
    Dann enthält die Playlist 'Favoriten' von 'Alice' 2 Lieder


#  Szenario: Playlist hinzufügen
#    Gegeben seien folgende Songs:
#    | Titel          | Interpret |
#    | Firestarter    | Prodigy   |
#    | Breathe        | Prodigy   |
#    Und folgende Benutzer:
#    | Name   |
#    | Alice  |
#    | Bob    |
#    Wenn der Benutzer 'Alice' den Song 'Firestarter' zu einer Playlist 'Favoriten' hinzufügt
#    Und der Benutzer 'Alice' den Song 'Breathe' zu einer Playlist 'Favoriten' hinzufügt
#    Dann enthält die Playlist 'Favoriten' von 'Alice' die Songs:
#    | Titel          | Interpret |
#    | Firestarter    | Prodigy   |
#    | Breathe        | Prodigy   |
