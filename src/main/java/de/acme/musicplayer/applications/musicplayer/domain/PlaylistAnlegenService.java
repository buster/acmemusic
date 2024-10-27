package de.acme.musicplayer.applications.musicplayer.domain;

import de.acme.musicplayer.applications.musicplayer.domain.model.Playlist;
import de.acme.musicplayer.applications.musicplayer.ports.PlaylistPort;
import de.acme.musicplayer.applications.musicplayer.usecases.PlaylistAnlegenUsecase;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.PlaylistId;
import de.acme.musicplayer.common.TenantId;
import org.springframework.transaction.annotation.Transactional;

public class PlaylistAnlegenService implements PlaylistAnlegenUsecase {

    private final PlaylistPort playlistPort;

    public PlaylistAnlegenService(PlaylistPort playlistPort) {
        this.playlistPort = playlistPort;
    }

    @Override
    @Transactional
    public PlaylistId playlistAnlegen(BenutzerId benutzerId, Playlist.Name playlistName, TenantId tenantId) {
        return playlistPort.erstellePlaylist(benutzerId, playlistName, tenantId);
    }
}
