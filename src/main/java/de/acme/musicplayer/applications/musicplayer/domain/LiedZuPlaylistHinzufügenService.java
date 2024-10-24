package de.acme.musicplayer.applications.musicplayer.domain;

import de.acme.musicplayer.applications.musicplayer.domain.model.Playlist;
import de.acme.musicplayer.applications.musicplayer.ports.PlaylistPort;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedZuPlaylistHinzufügenUsecase;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.PlaylistId;
import de.acme.musicplayer.common.TenantId;
import org.springframework.transaction.annotation.Transactional;

public class LiedZuPlaylistHinzufügenService implements LiedZuPlaylistHinzufügenUsecase {

    private final PlaylistPort playlistPort;

    public LiedZuPlaylistHinzufügenService(PlaylistPort playlistPort) {
        this.playlistPort = playlistPort;
    }

    @Override
    @Transactional
    public void liedZuPlaylistHinzufügen(BenutzerId benutzerId, LiedId liedId, PlaylistId playlistId, TenantId tenantId) {
        Playlist playlist = playlistPort.lade(playlistId, tenantId);
        playlist.liedHinzufügen(liedId, benutzerId);
        playlistPort.speichere(playlist, tenantId);
    }

}
