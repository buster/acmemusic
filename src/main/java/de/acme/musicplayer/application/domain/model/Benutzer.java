package de.acme.musicplayer.application.domain.model;


import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Benutzer {
    private final Name name;
    private final Email email;
    private Passwort passwort;

    public Benutzer(Name name, Passwort passwort, Email email) {
        this.name = name;
        this.passwort = passwort;
        this.email = email;
    }

    public static class Name {
        public final String benutzername;

        public Name(String benutzername) {
            checkArgument(isNotBlank(benutzername), "Benutzername darf nicht leer sein");
            this.benutzername = benutzername;
        }
    }

    public static class Passwort {
        private final String passwort;

        public Passwort(String passwort) {
            checkArgument(isNotBlank(passwort), "Passwort darf nicht leer sein");
            this.passwort = passwort;
        }
    }

    public static class Email {
        private final String email;

        public Email(String email) {
            this.email = email;
        }
    }
}
