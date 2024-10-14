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
    public Benutzer.Id registriereBenutzer(BenutzerRegistrierenCommand benutzerRegistrierenCommand) {
        Benutzer benutzer = new Benutzer(benutzerRegistrierenCommand.name(), benutzerRegistrierenCommand.passwort(), benutzerRegistrierenCommand.email());
        return benutzerPort.benutzerHinzufügen(benutzer);
    }
}
