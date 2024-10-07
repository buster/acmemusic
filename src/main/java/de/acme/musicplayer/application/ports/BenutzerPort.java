package de.acme.musicplayer.application.ports;

import de.acme.musicplayer.application.domain.model.Benutzer;

public interface BenutzerPort {
    void benutzerHinzufügen(Benutzer benutzer);

    long zaehleBenutzer();
}
