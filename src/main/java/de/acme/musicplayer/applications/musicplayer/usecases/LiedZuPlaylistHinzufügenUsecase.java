package de.acme.musicplayer.applications.musicplayer.usecases;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.PlaylistId;
import de.acme.musicplayer.common.TenantId;

@ModuleApi
public interface LiedZuPlaylistHinzufügenUsecase {
    void liedZuPlaylistHinzufügen(BenutzerId benutzername, LiedId liedId, PlaylistId playlistId, TenantId tenantId);
}
