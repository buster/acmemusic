package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.Playlist;

public interface PlaylistAdministrationUsecase {

    void löscheDatenbank();

    void löschePlaylist(Playlist.Id id);
}
