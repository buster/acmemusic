package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.ports.PlaylistPort;
import de.acme.musicplayer.application.usecases.PlaylistAdministrationUsecase;
import org.springframework.transaction.annotation.Transactional;

public class PlaylistAdministrationService implements PlaylistAdministrationUsecase {

    private final PlaylistPort playlistPort;

    public PlaylistAdministrationService(PlaylistPort playlistPort) {
        this.playlistPort = playlistPort;
    }

    @Override
    public void löscheDatenbank() {
        playlistPort.löscheDatenbank();
    }

    @Override
    @Transactional
    public void löschePlaylist(Playlist.Id id) {
        playlistPort.löschePlaylist(id);
    }
}
