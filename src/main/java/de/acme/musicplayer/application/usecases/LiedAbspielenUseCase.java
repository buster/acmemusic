package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.Lied;

public interface LiedAbspielenUseCase {
    void playSong(Lied.LiedId songId);
}
