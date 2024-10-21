package de.acme.musicplayer.applications.musicplayer.domain.model;

import de.acme.musicplayer.applications.users.domain.model.Benutzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Playlist {

    private final Name name;
    private final Benutzer.Id besitzer;
    private final List<Lied.Id> lieder = new ArrayList<>();
    private Id id;

    public Playlist(Benutzer.Id besitzerId, Name name) {
        this.name = name;
        this.besitzer = besitzerId;
        this.id = new Id(besitzerId.Id(), name.name);
    }

    public List<Lied.Id> getLieder() {
        return lieder;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public String getName() {
        return name.name;
    }

    public void liedHinzufügen(Lied.Id liedId, Benutzer.Id benutzerId) {
        if (lieder.stream().anyMatch(liedId::equals)) return;
        if (besitzer.equals(benutzerId)) {
            lieder.add(liedId);
        } else {
            throw new IllegalArgumentException("Nur der Besitzer kann Lieder hinzufügen");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (this.id == null) return false;
        if (this == o) return true;
        if (this.id.equals(((Playlist) o).id)) return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Benutzer.Id getBesitzer() {
        return besitzer;
    }

    public record Id(String id) {
        public Id(String benutzername, String playlistName) {
            this(String.format("%s-%s", benutzername, playlistName));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Id that = (Id) o;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }
    }

    public record Name(String name) {
    }
}
