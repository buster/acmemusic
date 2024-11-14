package de.acme.musicplayer.applications.musicplayer.domain.model;

import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.PlaylistId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Playlist {

    private final Name name;
    private final BenutzerId besitzer;
    private final List<LiedId> lieder = new ArrayList<>();
    private PlaylistId playlistId;

    public Playlist(BenutzerId besitzerId, Name name) {
        this.name = name;
        this.besitzer = besitzerId;
        this.playlistId = new PlaylistId(besitzerId.Id(), name.name);
    }

    public List<LiedId> getLieder() {
        return lieder;
    }

    public PlaylistId getId() {
        return playlistId;
    }

    public void setId(PlaylistId playlistId) {
        this.playlistId = playlistId;
    }

    public String getName() {
        return name.name;
    }

    public void liedHinzufügen(LiedId liedId, BenutzerId benutzerId) {
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
        if (this.playlistId == null) return false;
        if (this == o) return true;
        return this.playlistId.equals(((Playlist) o).playlistId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(playlistId);
    }

    public BenutzerId getBesitzer() {
        return besitzer;
    }

    public record Name(String name) {
    }
}
