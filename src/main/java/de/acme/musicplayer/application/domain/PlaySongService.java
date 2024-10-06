package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.ports.LoadSongPort;
import de.acme.musicplayer.application.usecases.PlaySongUseCase;

public class PlaySongService implements PlaySongUseCase {

    private final LoadSongPort loadSongPort;

    public PlaySongService(LoadSongPort loadSongPort) {
        this.loadSongPort = loadSongPort;
    }

    @Override
    public void playSong(String songId) {
        loadSongPort.loadSong(songId);
    }
}
