package de.acme.musicplayer.application.domain.model;

public class Benutzer {
    private final String benutzername;
    private final String passwort;
    private final String email;

    public Benutzer(String benutzername, String passwort, String email) {
        this.benutzername = benutzername;
        this.passwort = passwort;
        this.email = email;
    }
}
