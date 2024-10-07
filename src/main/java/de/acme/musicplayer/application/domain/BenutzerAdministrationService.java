package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.ports.BenutzerPort;
import de.acme.musicplayer.application.usecases.BenutzerAdministrationUsecase;

public class BenutzerAdministrationService implements BenutzerAdministrationUsecase {

    private final BenutzerPort benutzerPort;

    public BenutzerAdministrationService(BenutzerPort benutzerPort) {
        this.benutzerPort = benutzerPort;
    }

    @Override
    public long zähleBenutzer() {
        return benutzerPort.zaehleBenutzer();
    }
}
