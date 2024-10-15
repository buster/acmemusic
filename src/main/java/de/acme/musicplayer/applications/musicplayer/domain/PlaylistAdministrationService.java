package de.acme.musicplayer.applications.musicplayer.domain;

import de.acme.musicplayer.applications.musicplayer.ports.PlaylistPort;
import de.acme.musicplayer.applications.musicplayer.usecases.PlaylistAdministrationUsecase;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import org.springframework.transaction.annotation.Transactional;

public class PlaylistAdministrationService implements PlaylistAdministrationUsecase {

    private final PlaylistPort playlistPort;

    public PlaylistAdministrationService(PlaylistPort playlistPort) {
        this.playlistPort = playlistPort;
    }

    @Override
    @Transactional
    public void löscheDatenbank(TenantId tenantId) {
        playlistPort.löscheDatenbank(tenantId);
    }
}
