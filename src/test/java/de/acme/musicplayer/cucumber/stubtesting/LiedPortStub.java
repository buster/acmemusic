package de.acme.musicplayer.cucumber.stubtesting;

import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.ports.LiedPort;

import java.util.ArrayList;
import java.util.List;

public class LiedPortStub implements LiedPort {

    private final List<Lied> lieder = new ArrayList<>();

    @Override
    public Lied ladeLied(Lied.Id songId) {
        System.out.println("Load Lied Port Stub!");
        return lieder.get(Integer.parseInt(songId.id()));
    }

    @Override
    public long zähleLieder() {
        return lieder.size();
    }

    @Override
    public Lied.Id fügeLiedHinzu(Lied lied) {
        lied.setId(new Lied.Id(String.valueOf(lieder.size())));
        lieder.add(lied);
        return lied.getId();
    }

    @Override
    public void löscheDatenbank() {
        lieder.clear();
    }
}
