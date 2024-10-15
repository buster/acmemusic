package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.ports.PlaylistPort;
import de.acme.musicplayer.application.usecases.LiedZuPlaylistHinzufügenUsecase;

public class LiedZuPlaylistHinzufügenService implements LiedZuPlaylistHinzufügenUsecase {

    private final PlaylistPort playlistPort;

    public LiedZuPlaylistHinzufügenService(PlaylistPort playlistPort) {
        this.playlistPort = playlistPort;
    }

    @Override
    public void liedHinzufügen(Benutzer.Id benutzername, Lied.Id liedId, Playlist.Id playlistId) {
        Playlist playlist = playlistPort.lade(playlistId);
        playlistPort.fügeLiedHinzu(liedId, playlist.getId());
    }

}
