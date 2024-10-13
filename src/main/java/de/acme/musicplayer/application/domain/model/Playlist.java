package de.acme.musicplayer.application.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Playlist {

    private final String name;
    private PlaylistId id;
    private List<Lied.LiedId> lieder = new ArrayList<>();

    public Playlist(String benutzername, String playlistName) {
        this.name = playlistName;
        this.id = new PlaylistId(benutzername, playlistName);
    }

    public Playlist(String name, List<Lied.LiedId> lieder) {
        this.name = name;
        this.lieder = lieder;
    }

    public List<Lied.LiedId> getLieder() {
        return lieder;
    }

    public PlaylistId getId() {
        return id;
    }

    public void setId(PlaylistId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void liedHinzufügen(Lied.LiedId liedId) {
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

    public record PlaylistId(String id) {
        public PlaylistId(String benutzername, String playlistName) {
            this(String.format("%s-%s", benutzername, playlistName));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PlaylistId that = (PlaylistId) o;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }
    }

    public static class Name {
        private final String name;

        public Name(String name) {
            this.name = name;
        }
    }
}
