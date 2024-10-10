package de.acme.musicplayer.cucumber.stubtesting.test2real;

import de.acme.musicplayer.adapters.jpa.PlaylistJpaRepository;
import de.acme.musicplayer.adapters.jpa.PlaylistRepository;
import de.acme.musicplayer.adapters.jpa.SongJpaRepository;
import de.acme.musicplayer.adapters.jpa.SongRepository;
import de.acme.musicplayer.application.domain.*;
import de.acme.musicplayer.application.ports.BenutzerPort;
import de.acme.musicplayer.application.ports.LiedPort;
import de.acme.musicplayer.application.ports.PlaylistPort;
import de.acme.musicplayer.application.usecases.*;
import de.acme.musicplayer.cucumber.stubtesting.BenutzerPortStub;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class T2RConfiguration {


    // BEGIN: Adapter
    @Bean
    public PlaylistPort playlistPort(PlaylistJpaRepository playlistJpaRepository) {
        return new PlaylistRepository(playlistJpaRepository);
    }

    @Bean
    public BenutzerPort benutzerPort() {
        return new BenutzerPortStub();
    }

    @Bean
    public LiedPort liedPort(SongJpaRepository songJpaRepository) {
        return new SongRepository(songJpaRepository);
    }

    // END: Adapter

    @Bean
    public LiedAbspielenUseCase playSongUseCase(LiedPort liedPort) {
        return new LiedAbspielenService(liedPort);
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
    public LiedZuPlaylistHinzufügenUseCase liedZuPlaylistHinzufügenUseCase(PlaylistPort playlistPort) {
        return new LiedZuPlaylistHinzufügenService(playlistPort);
    }

    @Bean
    public LiedHochladenUseCase liedHochladenUseCase(LiedPort liedPort) {
        return new LiedHochladenService(liedPort);
    }

    @Bean
    public LiederInPlaylistAuflistenUsecase liederInPlaylistAuflistenUseCase(PlaylistPort playlistPort) {
        return new LiederInPlaylistAuflistenService(playlistPort);
    }
}
