package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.ports.LiedPort;
import de.acme.musicplayer.application.usecases.LiedAbspielenUseCase;

public class LiedAbspielenService implements LiedAbspielenUseCase {

    private final LiedPort liedPort;

    public LiedAbspielenService(LiedPort liedPort) {
        this.liedPort = liedPort;
    }

    @Override
    public void playSong(Lied.Id songId) {
        liedPort.ladeLied(songId);
    }
}
