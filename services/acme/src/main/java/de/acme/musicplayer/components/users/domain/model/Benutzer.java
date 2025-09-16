package de.acme.musicplayer.components.users.domain.model;


import de.acme.musicplayer.common.api.BenutzerId;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class Benutzer {
    private final Name name;
    private final Email email;
    private final Passwort passwort;
    private final BenutzerId benutzerId;
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

    public Set<Auszeichnung> getAuszeichnungen() {
        return auszeichnungen;
    }

    public void setAuszeichnungen(Set<Auszeichnung> auszeichnungen) {
        this.auszeichnungen = auszeichnungen;
    }

    public record Name(String benutzername) {
        public Name {
            if (isBlank(benutzername)) {
                throw new IllegalArgumentException("Benutzername darf nicht leer sein");
            }
        }
        }

    public record Passwort(String passwort) {
        public Passwort {
            if (isBlank(passwort)) {
                throw new IllegalArgumentException("Passwort darf nicht leer sein");
            }
        }
        }

    public record Email(String email) {
    }

}
