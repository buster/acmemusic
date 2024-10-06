package de.acme.musicplayer;

import de.acme.musicplayer.application.domain.*;
import de.acme.musicplayer.application.ports.BenutzerPort;
import de.acme.musicplayer.application.ports.LiedPort;
import de.acme.musicplayer.application.ports.PlaylistPort;
import de.acme.musicplayer.application.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AcmeConfiguration {

    @Bean
    public LiedAbspielenUsecase liedAbspielenUsecase(LiedPort liedPort) {
        return new LiedAbspielenService(liedPort);
    }

    @Bean
    public PlaylistAnlegenUsecase playlistAnlegenUsecase(PlaylistPort playlistPort) {
        return new PlaylistAnlegenService(playlistPort);
    }

    @Bean
    public PlaylistAdministrationUsecase playlistAdministrationUsecase(PlaylistPort playlistPort) {
        return new PlaylistAdministrationService(playlistPort);
    }

    @Bean
    public BenutzerRegistrierenUsecase benutzerRegistrierenUsecase(BenutzerPort benutzerPort) {
        return new BenutzerRegistrierenService(benutzerPort);
    }

    @Bean
    public BenutzerAdministrationUsecase benutzerAdministrationUsecase(BenutzerPort benutzerPort) {
        return new BenutzerAdministrationService(benutzerPort);
    }

    @Bean
    public LiedAdministrationUsecase liedAdministrationUsecase(LiedPort liedPort) {
        return new LiedAdministrationService(liedPort);
    }

    @Bean
    public LiedZuPlaylistHinzufügenUsecase liedZuPlaylistHinzufügenUseCase(PlaylistPort playlistPort) {
        return new LiedZuPlaylistHinzufügenService(playlistPort);
    }

    @Bean
    public LiedHochladenUsecase liedHochladenUseCase(LiedPort liedPort) {
        return new LiedHochladenService(liedPort);
    }

    @Bean
    public LiederInPlaylistAuflistenUsecase liederInPlaylistAuflistenUseCase(PlaylistPort playlistPort) {
        return new LiederInPlaylistAuflistenService(playlistPort);
    }
}