package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.domain.model.Song;
import de.acme.musicplayer.application.ports.AddSongToPlaylistPort;
import de.acme.musicplayer.application.usecases.AddSongToPlaylistUseCase;

public class AddSongToPlaylistService implements AddSongToPlaylistUseCase {

    private AddSongToPlaylistPort addSongToPlaylistPort;

    @Override
    public void addSongToPlaylist(String songId, String playlistId) {
    }
}
