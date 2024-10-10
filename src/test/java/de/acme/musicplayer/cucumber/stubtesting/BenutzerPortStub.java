package de.acme.musicplayer.cucumber.stubtesting;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.ports.BenutzerPort;

import java.util.ArrayList;
import java.util.List;

public class BenutzerPortStub implements BenutzerPort {

    private final List<Benutzer> benutzerList = new ArrayList<>();

    @Override
    public Benutzer.Id benutzerHinzufügen(Benutzer benutzer) {
        benutzerList.add(benutzer);
        return new Benutzer.Id(String.valueOf(benutzerList.size()));
    }

    @Override
    public long zaehleBenutzer() {
        return benutzerList.size();
    }

    @Override
    public void loescheDatenbank() {
        benutzerList.clear();
    }
}
