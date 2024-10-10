package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.domain.model.Benutzer;
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
    public void liedHinzufügen(Benutzer.Id benutzername, Lied.LiedId songId, Playlist.PlaylistId playlistId) {
        Playlist playlist = playlistPort.lade(playlistId);
        playlistPort.addSongToPlaylist(songId, playlist.getId());
    }

}
