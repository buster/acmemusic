package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.Lied;

public interface LiedZuPlaylistHinzufügenUseCase {
    void liedHinzufügen(String benutzername, Lied.LiedId songId, String playlistName);
}
