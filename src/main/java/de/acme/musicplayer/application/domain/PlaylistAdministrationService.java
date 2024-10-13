package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.ports.PlaylistPort;
import de.acme.musicplayer.application.usecases.PlaylistAdministrationUsecase;
import de.acme.musicplayer.application.domain.model.TenantId;
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
