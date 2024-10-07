package de.acme.musicplayer.application.ports;

import de.acme.musicplayer.application.domain.model.Artist;
import de.acme.musicplayer.application.domain.model.Lied;

public interface LiedPort {

    Lied ladeLied(String songId);

    void updateSong(Lied lied);

    Lied findSongByArtist(Artist artist);

    Long getPlayedSecondsByArtist(Artist artist);

    Long zähleLieder();
    void fügeLiedHinzu(Lied lied);

}
