package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.domain.model.TenantId;

import java.util.Collection;

public interface LiederAuflistenUsecase {

    Collection<Lied.Id> liederInPlaylistAuflisten(Benutzer.Id benutzerId, Playlist.Name playlistName, TenantId tenantId);
    Collection<Lied.Id> liederAuflisten(Benutzer.Id benutzerId, TenantId tenantId);
}
