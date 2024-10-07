package de.acme.musicplayer.application.domain.model;


import java.net.URI;

public class Lied {
    private String id;
    private String titel;
    private String interpret;
    private String album;
    private String genre;
    private String erscheinungsjahr;
    private URI uri;

    public Lied(String titel, String interpret, String album, String genre, String erscheinungsjahr, URI uri) {
        this.titel = titel;
        this.interpret = interpret;
        this.album = album;
        this.genre = genre;
        this.erscheinungsjahr = erscheinungsjahr;
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
