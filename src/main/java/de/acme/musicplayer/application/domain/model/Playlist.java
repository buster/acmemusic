package de.acme.musicplayer.application.domain.model;

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

    public void liedHinzufügen(Lied.Id liedId) {
        if (lieder.stream().anyMatch(liedId::equals)) return;
        this.lieder.add(liedId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return Objects.equals(id, playlist.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
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
