package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.ports.LiedPort;
import de.acme.musicplayer.application.ports.PlaylistPort;
import de.acme.musicplayer.application.usecases.LiederAuflistenUsecase;
import de.acme.musicplayer.application.domain.model.TenantId;

import java.util.Collection;
import java.util.List;

public class LiederAuflistenService implements LiederAuflistenUsecase {

    private final PlaylistPort playlistPort;
    private final LiedPort liedPort;

    public LiederAuflistenService(PlaylistPort playlistPort, LiedPort liedPort) {
        this.playlistPort = playlistPort;
        this.liedPort = liedPort;
    }

    @Override
    public Collection<Lied.Id> liederInPlaylistAuflisten(Benutzer.Id benutzerId, Playlist.Name playlistName, TenantId tenantId) {
        return playlistPort.lade(benutzerId, playlistName, tenantId).getLieder();
    }

    @Override
    public Collection<Lied.Id> liederAuflisten(Benutzer.Id benutzerId, TenantId tenantId) {
        return liedPort.listeLiederAuf(benutzerId, tenantId);
    }
}
