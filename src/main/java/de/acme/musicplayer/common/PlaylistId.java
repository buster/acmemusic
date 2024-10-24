package de.acme.musicplayer.common;

import de.acme.musicplayer.ModuleApi;

import java.util.Objects;

@ModuleApi
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
