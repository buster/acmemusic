package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.ports.LiedHinzufügenPort;
import de.acme.musicplayer.application.usecases.LiedHochladenUseCase;

import java.net.URI;

public class LiedHochladenService implements LiedHochladenUseCase {

    private final LiedHinzufügenPort liedHinzufügenPort;

    public LiedHochladenService(LiedHinzufügenPort liedHinzufügenPort) {
        this.liedHinzufügenPort = liedHinzufügenPort;
    }

    @Override
    public void liedHochladen(String title, String artist, String album, String genre, String releaseYear, URI uri) {
        liedHinzufügenPort.fügeLiedHinzu(new Lied(title, artist, album, genre, releaseYear, uri));
    }
}
