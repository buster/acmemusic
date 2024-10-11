package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.ports.LiedPort;
import de.acme.musicplayer.application.usecases.LiedHochladenUseCase;

public class LiedHochladenService implements LiedHochladenUseCase {

    private final LiedPort liedPort;

    public LiedHochladenService(LiedPort liedPort) {
        this.liedPort = liedPort;
    }

    @Override
    public Lied.Id liedHochladen(String title) {
        return liedPort.fügeLiedHinzu(new Lied(new Lied.Titel(title)));
    }
}
