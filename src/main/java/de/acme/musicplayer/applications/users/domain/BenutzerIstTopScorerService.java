package de.acme.musicplayer.applications.users.domain;

import de.acme.musicplayer.applications.users.ports.BenutzerPort;
import de.acme.musicplayer.applications.users.usecases.BenutzerIstTopScorerUsecase;
import de.acme.musicplayer.events.NeuerTopScorerEvent;

public class BenutzerIstTopScorerService implements BenutzerIstTopScorerUsecase {

    private final BenutzerPort benutzerPort;

    public BenutzerIstTopScorerService(BenutzerPort benutzerPort) {
        this.benutzerPort = benutzerPort;
    }

    @Override
    public void neuerTopScorerGefunden(NeuerTopScorerEvent event) {

    }
}
