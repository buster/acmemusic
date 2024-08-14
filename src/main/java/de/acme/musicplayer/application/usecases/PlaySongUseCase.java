package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.Song;

public interface PlaySongUseCase {
    void playSong(String songId);
}
