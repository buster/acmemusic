package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;

public interface LiedZuPlaylistHinzufügenUseCase {
    void liedHinzufügen(Benutzer.Id benutzername, Lied.Id songId, Playlist.Id playlistId);
}
