package de.acme.musicplayer.adapters.jpa;

import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.ports.PlaylistPort;

public class PlaylistRepository implements PlaylistPort {

    private PlaylistJpaRepository playlistJpaRepository;

    @Override
    public void addSongToPlaylist(String songId, String playlistId) {
    }

    @Override
    public Playlist lade(String benutzername, String playlistName) {

        return null;
    }
}
