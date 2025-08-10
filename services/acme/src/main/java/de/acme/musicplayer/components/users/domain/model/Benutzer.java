package de.acme.musicplayer.components.users.domain.model;


import de.acme.musicplayer.common.api.BenutzerId;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Benutzer {
    private final Name name;
    private final Email email;
    private final Passwort passwort;
    private BenutzerId benutzerId;
    private Set<Auszeichnung> auszeichnungen = new HashSet<>();

    public Benutzer(Name name, Passwort passwort, Email email) {
        this.benutzerId = new BenutzerId(UUID.randomUUID().toString());
        this.name = name;
        this.passwort = passwort;
        this.email = email;
    }

    public Benutzer(BenutzerId id, Name name, Passwort passwort, Email email) {
        this.benutzerId = id;
        this.name = name;
        this.passwort = passwort;
        this.email = email;
    }

    public Name getName() {
        return name;
    }

    public Passwort getPasswort() {
        return passwort;
    }

    public Email getEmail() {
        return email;
    }

    public BenutzerId getId() {
        return benutzerId;
    }

    public void setId(BenutzerId benutzerId) {
        this.benutzerId = benutzerId;
    }

    public Set<Auszeichnung> getAuszeichnungen() {
        return auszeichnungen;
    }

    public void setAuszeichnungen(Set<Auszeichnung> auszeichnungen) {
        this.auszeichnungen = auszeichnungen;
    }

    public static class Name {
        public final String benutzername;

        public Name(String benutzername) {
            Objects.requireNonNull(benutzername, "Benutzername darf nicht null sein");
            if (benutzername.isBlank()) {
                throw new IllegalArgumentException("Benutzername darf nicht leer sein");
            }
            this.benutzername = benutzername;
        }
    }

    public static class Passwort {
        public final String passwort;

        public Passwort(String passwort) {
            Objects.requireNonNull(passwort, "Passwort darf nicht null sein");
            if (passwort.isBlank()) {
                throw new IllegalArgumentException("Passwort darf nicht leer sein");
            }
            this.passwort = passwort;
        }
    }

    public static class Email {
        public final String email;

        public Email(String email) {
            this.email = email;
        }
    }

}
