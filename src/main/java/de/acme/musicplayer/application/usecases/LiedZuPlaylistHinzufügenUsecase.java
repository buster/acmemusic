package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.domain.model.TenantId;

public interface LiedZuPlaylistHinzufügenUsecase {
    void liedZuPlaylistHinzufügen(Benutzer.Id benutzername, Lied.Id liedId, Playlist.Id playlistId, TenantId tenantId);
}
