package de.acme.musicplayer.adapters.jpa;

import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.ports.PlaylistPort;

public class PlaylistRepository implements PlaylistPort {

    private final PlaylistJpaRepository playlistJpaRepository;

    public PlaylistRepository(PlaylistJpaRepository playlistJpaRepository) {
        this.playlistJpaRepository = playlistJpaRepository;
    }

    @Override
    public void addSongToPlaylist(Lied.LiedId liedId, Playlist.PlaylistId playlistId) {
    }

    @Override
    public Playlist lade(String benutzername, String playlistName) {
        return null;
    }
}
