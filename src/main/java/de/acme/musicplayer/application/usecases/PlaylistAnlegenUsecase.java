package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Playlist;

public interface PlaylistAnlegenUsecase {
    Playlist.Id playlistAnlegen(Benutzer.Id benutzername, Playlist.Name name);
}
