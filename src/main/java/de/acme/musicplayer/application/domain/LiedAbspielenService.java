package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.ports.LiedLadenPort;
import de.acme.musicplayer.application.usecases.LiedAbspielenUseCase;

public class LiedAbspielenService implements LiedAbspielenUseCase {

    private final LiedLadenPort liedLadenPort;

    public LiedAbspielenService(LiedLadenPort liedLadenPort) {
        this.liedLadenPort = liedLadenPort;
    }

    @Override
    public void playSong(String songId) {
        liedLadenPort.ladeLied(songId);
    }
}
