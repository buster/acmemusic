package de.acme.musicplayer.adapters.jpa;

import de.acme.musicplayer.application.ports.AddSongToPlaylistPort;

public class PlaylistRepository implements AddSongToPlaylistPort {

    private PlaylistJpaRepository playlistJpaRepository;

    @Override
    public void addSongToPlaylist(String songId, String playlistId) {
    }
}
