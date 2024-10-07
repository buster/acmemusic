package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.ports.BenutzerHinzufügenPort;
import de.acme.musicplayer.application.usecases.BenutzerRegistrierenUsecase;

public class BenutzerRegistrierenService implements BenutzerRegistrierenUsecase {

    private final BenutzerHinzufügenPort benutzerHinzufügenPort;

    public BenutzerRegistrierenService(BenutzerHinzufügenPort benutzerHinzufügenPort) {
        this.benutzerHinzufügenPort = benutzerHinzufügenPort;
    }

    @Override
    public void benutzerAnmelden(String benutzername, String passwort, String email) {
            benutzerHinzufügenPort.benutzerHinzufügen(new Benutzer(benutzername, passwort, email));
    }
}
