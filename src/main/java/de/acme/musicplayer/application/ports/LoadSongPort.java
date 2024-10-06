package de.acme.musicplayer.application.ports;

import de.acme.musicplayer.application.domain.model.Artist;
import de.acme.musicplayer.application.domain.model.Song;

public interface LoadSongPort {

    Song loadSong(String songId);

    void updateSong(Song song);

    Song findSongByArtist(Artist artist);

    Long getPlayedSecondsByArtist(Artist artist);
}
