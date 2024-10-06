package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.ports.PlaylistPort;
import de.acme.musicplayer.application.usecases.LiederInPlaylistAuflistenUsecase;

import java.util.Collection;

public class LiederInPlaylistAuflistenService implements LiederInPlaylistAuflistenUsecase {

    private final PlaylistPort playlistPort;

    public LiederInPlaylistAuflistenService(PlaylistPort playlistPort) {
        this.playlistPort = playlistPort;
    }

    @Override
    public Collection<Lied.Id> liederAuflisten(Benutzer.Id benutzerId, Playlist.Name playlistName) {
        return playlistPort.lade(benutzerId, playlistName).getLieder();
    }
}
