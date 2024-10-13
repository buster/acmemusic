package de.acme.musicplayer.application.ports;

import de.acme.musicplayer.application.domain.model.Benutzer;

public interface BenutzerPort {
    Benutzer.Id benutzerHinzufügen(Benutzer benutzer);

    long zaehleBenutzer();

    void loescheDatenbank();

    void löscheBenutzer(Benutzer.Id id);
}
