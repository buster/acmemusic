package de.acme.musicplayer.applications.musicplayer.usecases;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.domain.model.Playlist;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
@ModuleApi
public interface LiedZuPlaylistHinzufügenUsecase {
    void liedZuPlaylistHinzufügen(Benutzer.Id benutzername, Lied.Id liedId, Playlist.Id playlistId, TenantId tenantId);
}
