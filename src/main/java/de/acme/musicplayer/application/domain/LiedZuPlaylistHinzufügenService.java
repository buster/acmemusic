package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.ports.PlaylistPort;
import de.acme.musicplayer.application.usecases.LiedZuPlaylistHinzufügenUseCase;

public class LiedZuPlaylistHinzufügenService implements LiedZuPlaylistHinzufügenUseCase {

    private final PlaylistPort playlistPort;

    public LiedZuPlaylistHinzufügenService(PlaylistPort playlistPort) {
        this.playlistPort = playlistPort;
    }

    @Override
    public void liedHinzufügen(String benutzername, Lied.LiedId songId, String playlistName) {
        Playlist playlist = playlistPort.lade(benutzername, playlistName);
        playlistPort.addSongToPlaylist(songId, playlist.getId());
    }
}
