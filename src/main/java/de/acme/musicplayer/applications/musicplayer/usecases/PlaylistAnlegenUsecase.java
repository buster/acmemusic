package de.acme.musicplayer.applications.musicplayer.usecases;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.applications.musicplayer.domain.model.Playlist;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.PlaylistId;
import de.acme.musicplayer.common.TenantId;

@ModuleApi
public interface PlaylistAnlegenUsecase {
    PlaylistId playlistAnlegen(BenutzerId benutzername, Playlist.Name name, TenantId tenantId);
}
