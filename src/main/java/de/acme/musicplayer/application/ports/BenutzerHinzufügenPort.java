package de.acme.musicplayer.application.ports;

import de.acme.musicplayer.application.domain.model.Benutzer;

public interface BenutzerHinzufügenPort {

    void benutzerHinzufügen(Benutzer benutzer);
}
