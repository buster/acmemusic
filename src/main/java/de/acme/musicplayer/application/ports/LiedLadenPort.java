package de.acme.musicplayer.application.ports;

import de.acme.musicplayer.application.domain.model.Artist;
import de.acme.musicplayer.application.domain.model.Song;

public interface LiedLadenPort {

    Song ladeLied(String songId);

    void updateSong(Song song);

    Song findSongByArtist(Artist artist);

    Long getPlayedSecondsByArtist(Artist artist);

    Long zähleLieder();
}
