package de.acme.musicplayer.application.ports;

public interface BenutzerHinzufügenPort {

    void benutzerHinzufügen(String benutzername, String passwort, String email);
}
