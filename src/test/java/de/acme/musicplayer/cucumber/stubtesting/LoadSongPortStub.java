package de.acme.musicplayer.cucumber.stubtesting;

import de.acme.musicplayer.application.domain.model.Artist;
import de.acme.musicplayer.application.domain.model.Song;
import de.acme.musicplayer.application.ports.LoadSongPort;

public class LoadSongPortStub implements LoadSongPort {
    @Override
    public Song loadSong(String songId) {
        System.out.println("Load Song Port Stub!");
        return null;
    }

    @Override
    public void updateSong(Song song) {

    }

    @Override
    public Song findSongByArtist(Artist artist) {
        return null;
    }

    @Override
    public Long getPlayedSecondsByArtist(Artist artist) {
        return 0L;
    }
}
