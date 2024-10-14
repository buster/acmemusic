package de.acme.musicplayer.applications.musicplayer.domain;

import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.domain.model.Playlist;
import de.acme.musicplayer.applications.musicplayer.ports.PlaylistPort;
import de.acme.musicplayer.applications.musicplayer.usecases.LiederInPlaylistAuflistenUsecase;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;

import java.util.Collection;

public class LiederInPlaylistAuflistenService implements LiederInPlaylistAuflistenUsecase {

    private final PlaylistPort playlistPort;

    public LiederInPlaylistAuflistenService(PlaylistPort playlistPort) {
        this.playlistPort = playlistPort;
    }

    @Override
    public Collection<Lied.Id> liederInPlaylistAuflisten(Benutzer.Id benutzerId, Playlist.Name playlistName, TenantId tenantId) {
        return playlistPort.lade(benutzerId, playlistName, tenantId).getLieder();
    }

}
