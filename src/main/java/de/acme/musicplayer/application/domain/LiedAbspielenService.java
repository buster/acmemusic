package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.ports.LiedPort;
import de.acme.musicplayer.application.usecases.LiedAbspielenUsecase;

public class LiedAbspielenService implements LiedAbspielenUsecase {

    private final LiedPort liedPort;

    public LiedAbspielenService(LiedPort liedPort) {
        this.liedPort = liedPort;
    }

    @Override
    public void spieleLiedAb(Lied.Id liedId) {
        liedPort.ladeLied(liedId);
    }
}
