package de.acme.musicplayer.applications.musicplayer.usecases;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.applications.musicplayer.domain.model.Playlist;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
@ModuleApi
public interface PlaylistAnlegenUsecase {
    Playlist.Id playlistAnlegen(Benutzer.Id benutzername, Playlist.Name name, TenantId tenantId);
}
