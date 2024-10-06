package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.ports.PlaylistPort;
import de.acme.musicplayer.application.usecases.LiedZuPlaylistHinzufügenUseCase;

public class LiedZuPlaylistHinzufügenService implements LiedZuPlaylistHinzufügenUseCase {

    private final PlaylistPort playlistPort;

    public LiedZuPlaylistHinzufügenService(PlaylistPort playlistPort) {
        this.playlistPort = playlistPort;
    }

    @Override
    public void addSongToPlaylist(String benutzername, String songId, String playlistName) {
        Playlist playlist = playlistPort.lade(benutzername, playlistName);
        if (playlist != null) {
//            playlist.liedHinzufügen(songId);
//            playlistPort.addSongToPlaylist(songId, playlistName);
        }
    }
}
