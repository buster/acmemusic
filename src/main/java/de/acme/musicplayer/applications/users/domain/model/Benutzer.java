package de.acme.musicplayer.applications.users.domain.model;


import de.acme.musicplayer.ModuleApi;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Benutzer {
    private final Name name;
    private final Email email;
    private Benutzer.Id id;
    private final Passwort passwort;
    public Benutzer(Name name, Passwort passwort, Email email) {
        this.name = name;
        this.passwort = passwort;
        this.email = email;
//        this.id = new Id(email.email);
    }

    public void setId(Id id) {
        this.id = id;
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

    public Id getId() {
        return id;
    }

    public static class Name {
        public final String benutzername;

        public Name(String benutzername) {
            checkArgument(isNotBlank(benutzername), "Benutzername darf nicht leer sein");
            this.benutzername = benutzername;
        }
    }

    public static class Passwort {
        public final String passwort;

        public Passwort(String passwort) {
            checkArgument(isNotBlank(passwort), "Passwort darf nicht leer sein");
            this.passwort = passwort;
        }
    }

    public static class Email {
        public final String email;

        public Email(String email) {
            this.email = email;
        }
    }

    @ModuleApi
    public record Id(String Id) {
        public Id(String Id) {
            checkNotNull(Id);
            this.Id = Id;
        }
    }
}
