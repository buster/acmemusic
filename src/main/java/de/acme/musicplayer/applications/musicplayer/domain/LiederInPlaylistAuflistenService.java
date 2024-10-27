package de.acme.musicplayer.applications.musicplayer.domain;

import de.acme.musicplayer.applications.musicplayer.domain.model.Playlist;
import de.acme.musicplayer.applications.musicplayer.ports.PlaylistPort;
import de.acme.musicplayer.applications.musicplayer.usecases.LiederInPlaylistAuflistenUsecase;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.TenantId;

import java.util.Collection;

public class LiederInPlaylistAuflistenService implements LiederInPlaylistAuflistenUsecase {

    private final PlaylistPort playlistPort;

    public LiederInPlaylistAuflistenService(PlaylistPort playlistPort) {
        this.playlistPort = playlistPort;
    }

    @Override
    public Collection<LiedId> liederInPlaylistAuflisten(BenutzerId benutzerId, Playlist.Name playlistName, TenantId tenantId) {
        return playlistPort.lade(benutzerId, playlistName, tenantId).getLieder();
    }

}
