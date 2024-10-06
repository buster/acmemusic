package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.ports.AddSongToPlaylistPort;
import de.acme.musicplayer.application.usecases.LiedZuPlaylistHinzufügenUseCase;

public class LiedZuPlaylistHinzufügenService implements LiedZuPlaylistHinzufügenUseCase {

    private AddSongToPlaylistPort addSongToPlaylistPort;

    @Override
    public void addSongToPlaylist(String songId, String playlistId) {
    }
}
