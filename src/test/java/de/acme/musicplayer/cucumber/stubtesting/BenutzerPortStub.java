package de.acme.musicplayer.cucumber.stubtesting;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.ports.BenutzerPort;

import java.util.*;

public class BenutzerPortStub implements BenutzerPort {

    private final Map<String, Benutzer> benutzerList = new HashMap<>();

    @Override
    public Benutzer.Id benutzerHinzufügen(Benutzer benutzer) {
        Benutzer.Id id = new Benutzer.Id(UUID.randomUUID().toString());
        benutzer.setId(id);
        benutzerList.put(id.Id(), benutzer);
        return id;
    }

    @Override
    public long zaehleBenutzer() {
        return benutzerList.size();
    }

    @Override
    public void loescheDatenbank() {
        benutzerList.clear();
    }

    @Override
    public void löscheBenutzer(Benutzer.Id id) {
        benutzerList.remove(id.Id());
    }
}
