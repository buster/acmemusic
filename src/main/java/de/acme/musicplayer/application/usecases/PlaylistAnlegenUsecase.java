package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.domain.model.TenantId;

public interface PlaylistAnlegenUsecase {
    Playlist.Id playlistAnlegen(Benutzer.Id benutzername, Playlist.Name name, TenantId tenantId);
}
