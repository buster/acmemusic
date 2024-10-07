package de.acme.musicplayer.cucumber.stubtesting;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.ports.BenutzerHinzufügenPort;

import java.util.ArrayList;
import java.util.List;

public class BenutzerHinzufügenPortStub implements BenutzerHinzufügenPort {

    private List<Benutzer> benutzerList = new ArrayList<>();

    @Override
    public void benutzerHinzufügen(Benutzer benutzer) {
        benutzerList.add(benutzer);
    }
}
