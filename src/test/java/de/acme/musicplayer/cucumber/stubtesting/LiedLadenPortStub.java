package de.acme.musicplayer.cucumber.stubtesting;

import de.acme.musicplayer.application.domain.model.Artist;
import de.acme.musicplayer.application.domain.model.Song;
import de.acme.musicplayer.application.ports.LiedLadenPort;

public class LiedLadenPortStub implements LiedLadenPort {



    @Override
    public Song ladeLied(String songId) {
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

    @Override
    public Long zähleLieder() {
        return 0L;
    }
}
