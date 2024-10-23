package de.acme.musicplayer.applications.musicplayer.usecases;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.applications.musicplayer.domain.model.Playlist;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.TenantId;

import java.util.Collection;
@ModuleApi
public interface LiederInPlaylistAuflistenUsecase {

    Collection<LiedId> liederInPlaylistAuflisten(BenutzerId benutzerId, Playlist.Name playlistName, TenantId tenantId);
}
