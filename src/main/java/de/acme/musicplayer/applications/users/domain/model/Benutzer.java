package de.acme.musicplayer.applications.users.domain.model;


import com.google.common.base.Objects;
import de.acme.musicplayer.ModuleApi;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Benutzer {
    private final Name name;
    private final Email email;
    private final Passwort passwort;
    private Id id;
    private Set<Auszeichnung> auszeichnungen = new HashSet<>();

    public Benutzer(Name name, Passwort passwort, Email email) {
        this.name = name;
        this.passwort = passwort;
        this.email = email;
//        this.id = new Id(email.email);
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

    public void setId(Id id) {
        this.id = id;
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
