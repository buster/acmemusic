package de.acme.musicplayer.applications.musicplayer.usecases;

import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.domain.model.Playlist;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;

import java.util.Collection;

public interface LiederInPlaylistAuflistenUsecase {

    Collection<Lied.Id> liederInPlaylistAuflisten(Benutzer.Id benutzerId, Playlist.Name playlistName, TenantId tenantId);
}
