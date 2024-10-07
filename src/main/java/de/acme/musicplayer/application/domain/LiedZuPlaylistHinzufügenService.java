package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.ports.PlaylistPort;
import de.acme.musicplayer.application.usecases.LiedZuPlaylistHinzufügenUsecase;
import de.acme.musicplayer.application.domain.model.TenantId;
import org.springframework.transaction.annotation.Transactional;

public class LiedZuPlaylistHinzufügenService implements LiedZuPlaylistHinzufügenUsecase {

    private final PlaylistPort playlistPort;

    public LiedZuPlaylistHinzufügenService(PlaylistPort playlistPort) {
        this.playlistPort = playlistPort;
    }

    @Override
    @Transactional
    public void liedHinzufügen(Benutzer.Id benutzername, Lied.Id liedId, Playlist.Id playlistId, TenantId tenantId) {
        Playlist playlist = playlistPort.lade(playlistId, tenantId);
        playlistPort.fügeLiedHinzu(liedId, playlist.getId(), tenantId);
    }

}
