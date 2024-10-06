package de.acme.musicplayer.application.domain.model;

import java.util.List;

public class Playlist {


    private List<String> lieder;

    private String name;

    private String besitzer;

    public Playlist(String name, List<String> lieder) {
        this.name = name;
        this.lieder = lieder;
    }

    public String getBesitzer() {
        return besitzer;
    }

    public void setBesitzer(String besitzer) {
        this.besitzer = besitzer;
    }

    public void liedHinzufügen(String liedId) {
        lieder.add(liedId);
    }
}
