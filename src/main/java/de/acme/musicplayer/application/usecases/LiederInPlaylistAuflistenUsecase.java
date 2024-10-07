package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.Lied;

import java.util.Collection;

public interface LiederInPlaylistAuflistenUsecase {

    Collection<Lied.LiedId> liederAuflisten(String benutzerId, String playlistName);
}
