package de.acme.musicplayer.applications.musicplayer.domain;

import de.acme.musicplayer.applications.musicplayer.ports.PlaylistPort;
import de.acme.musicplayer.applications.musicplayer.usecases.PlaylistAdministrationUsecase;
import de.acme.musicplayer.common.TenantId;

public class PlaylistAdministrationService implements PlaylistAdministrationUsecase {

    private final PlaylistPort playlistPort;

    public PlaylistAdministrationService(PlaylistPort playlistPort) {
        this.playlistPort = playlistPort;
    }

    @Override
    public void löscheDatenbank(TenantId tenantId) {
        playlistPort.löscheDatenbank(tenantId);
    }
}
