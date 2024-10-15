package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;

import java.util.Collection;

public interface LiederInPlaylistAuflistenUsecase {

    Collection<Lied.Id> liederAuflisten(Benutzer.Id benutzerId, Playlist.Name playlistName);
}
