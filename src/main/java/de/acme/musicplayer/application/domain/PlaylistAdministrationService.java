package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.ports.PlaylistPort;
import de.acme.musicplayer.application.usecases.PlaylistAdministrationUsecase;

public class PlaylistAdministrationService implements PlaylistAdministrationUsecase {

    private final PlaylistPort playlistPort;

    public PlaylistAdministrationService(PlaylistPort playlistPort) {
        this.playlistPort = playlistPort;
    }

    @Override
    public void löscheDatenbank() {
        playlistPort.löscheDatenbank();
    }
}
