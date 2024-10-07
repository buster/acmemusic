package de.acme.musicplayer.cucumber.stubtesting;

import de.acme.musicplayer.application.domain.model.Artist;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.ports.LiedPort;

import java.util.ArrayList;
import java.util.List;

public class LiedPortStub implements LiedPort {

    private final List<Lied> lieder = new ArrayList<Lied>();

    @Override
    public Lied ladeLied(String songId) {
        System.out.println("Load Lied Port Stub!");
        return lieder.get(Integer.parseInt(songId));
    }

    @Override
    public void updateSong(Lied lied) {

    }

    @Override
    public Lied findSongByArtist(Artist artist) {
        return null;
    }

    @Override
    public Long getPlayedSecondsByArtist(Artist artist) {
        return 0L;
    }

    @Override
    public Long zähleLieder() {
        return Long.valueOf(lieder.size());
    }

    @Override
    public void fügeLiedHinzu(Lied lied) {
        lied.setId(String.valueOf(lieder.size()));
        lieder.add(lied);
        System.out.println("Add Lied Port Stub!");
    }
}
