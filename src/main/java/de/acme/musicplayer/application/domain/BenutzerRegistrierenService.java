package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.ports.BenutzerPort;
import de.acme.musicplayer.application.usecases.BenutzerRegistrierenUsecase;

public class BenutzerRegistrierenService implements BenutzerRegistrierenUsecase {

    private final BenutzerPort benutzerPort;

    public BenutzerRegistrierenService(BenutzerPort benutzerPort) {
        this.benutzerPort = benutzerPort;
    }

    @Override
    public void benutzerAnmelden(BenutzerAnmeldenCommand benutzerAnmeldenCommand) {
        benutzerPort.benutzerHinzufügen(new Benutzer(benutzerAnmeldenCommand.name(), benutzerAnmeldenCommand.passwort(), benutzerAnmeldenCommand.email()));
    }
}
