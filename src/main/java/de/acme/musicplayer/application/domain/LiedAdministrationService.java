package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.ports.LiedPort;
import de.acme.musicplayer.application.usecases.LiedAdministrationUsecase;

public class LiedAdministrationService implements LiedAdministrationUsecase {

    private final LiedPort liedPort;

    public LiedAdministrationService(LiedPort liedPort) {
        this.liedPort = liedPort;
    }

    @Override
    public long zähleLieder() {
        return liedPort.zähleLieder();
    }

    @Override
    public void löscheDatenbank() {
        liedPort.löscheDatenbank();
    }
}
