package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.ports.PlaylistPort;
import de.acme.musicplayer.application.usecases.PlaylistAnlegenUsecase;

public class PlaylistAnlegenService implements PlaylistAnlegenUsecase {

    private final PlaylistPort playlistPort;

    public PlaylistAnlegenService(PlaylistPort playlistPort) {
        this.playlistPort = playlistPort;
    }

    @Override
    public Playlist.Id playlistAnlegen(Benutzer.Id benutzername, Playlist.Name name) {
        return playlistPort.erstellePlaylist(benutzername, name);
    }
}
