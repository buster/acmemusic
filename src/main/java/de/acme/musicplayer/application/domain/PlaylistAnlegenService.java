package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.ports.PlaylistPort;
import de.acme.musicplayer.application.usecases.PlaylistAnlegenUsecase;
import de.acme.musicplayer.application.domain.model.TenantId;
import org.springframework.transaction.annotation.Transactional;

public class PlaylistAnlegenService implements PlaylistAnlegenUsecase {

    private final PlaylistPort playlistPort;

    public PlaylistAnlegenService(PlaylistPort playlistPort) {
        this.playlistPort = playlistPort;
    }

    @Override
    @Transactional
    public Playlist.Id playlistAnlegen(Benutzer.Id benutzername, Playlist.Name name, TenantId tenantId) {
        return playlistPort.erstellePlaylist(benutzername, name, tenantId);
    }
}
