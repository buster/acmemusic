package de.acme.musicplayer.applications.musicplayer.domain;

import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.domain.model.Playlist;
import de.acme.musicplayer.applications.musicplayer.ports.PlaylistPort;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedZuPlaylistHinzufügenUsecase;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import org.springframework.transaction.annotation.Transactional;

public class LiedZuPlaylistHinzufügenService implements LiedZuPlaylistHinzufügenUsecase {

    private final PlaylistPort playlistPort;

    public LiedZuPlaylistHinzufügenService(PlaylistPort playlistPort) {
        this.playlistPort = playlistPort;
    }

    @Override
    @Transactional
    public void liedZuPlaylistHinzufügen(Benutzer.Id benutzerId, Lied.Id liedId, Playlist.Id playlistId, TenantId tenantId) {
        Playlist playlist = playlistPort.lade(playlistId, tenantId);
        playlist.liedHinzufügen(liedId, benutzerId);
        playlistPort.speichere(playlist, tenantId);
    }

}
