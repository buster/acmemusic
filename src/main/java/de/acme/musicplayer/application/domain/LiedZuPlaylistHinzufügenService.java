package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.ports.PlaylistPort;
import de.acme.musicplayer.application.usecases.LiedZuPlaylistHinzufügenUseCase;

public class LiedZuPlaylistHinzufügenService implements LiedZuPlaylistHinzufügenUseCase {

    private PlaylistPort playlistPort;

    @Override
    public void addSongToPlaylist(String songId, String playlistId) {
    }
}
