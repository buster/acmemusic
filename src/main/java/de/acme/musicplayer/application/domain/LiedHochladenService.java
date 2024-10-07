package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.ports.LiedPort;
import de.acme.musicplayer.application.usecases.LiedHochladenUseCase;

import java.net.URI;

public class LiedHochladenService implements LiedHochladenUseCase {

    private final LiedPort liedPort;

    public LiedHochladenService(LiedPort liedPort) {
        this.liedPort = liedPort;
    }

    @Override
    public void liedHochladen(String title, String artist, String album, String genre, String releaseYear, URI uri) {
        liedPort.fügeLiedHinzu(new Lied(title, artist, album, genre, releaseYear, uri));
    }
}
