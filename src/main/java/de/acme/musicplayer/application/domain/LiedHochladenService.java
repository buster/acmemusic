package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.ports.LiedPort;
import de.acme.musicplayer.application.usecases.LiedHochladenUsecase;

import java.io.IOException;
import java.io.InputStream;

public class LiedHochladenService implements LiedHochladenUsecase {

    private final LiedPort liedPort;

    public LiedHochladenService(LiedPort liedPort) {
        this.liedPort = liedPort;
    }

    @Override
    public Lied.Id liedHochladen(Lied.Titel title, InputStream lied) throws IOException {
        return liedPort.fügeLiedHinzu(new Lied(title), lied);
    }
}
