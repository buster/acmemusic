package de.acme.musicplayer.cucumber.stubtesting.test2test;

import de.acme.musicplayer.application.domain.*;
import de.acme.musicplayer.application.ports.BenutzerPort;
import de.acme.musicplayer.application.ports.LiedPort;
import de.acme.musicplayer.application.ports.PlaylistPort;
import de.acme.musicplayer.application.usecases.*;
import de.acme.musicplayer.cucumber.stubtesting.BenutzerPortStub;
import de.acme.musicplayer.cucumber.stubtesting.LiedPortStub;
import de.acme.musicplayer.cucumber.stubtesting.PlaylistPortStub;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class T2TConfiguration {

    // Adapter
    @Bean
    public PlaylistPort playlistPort() {
        return new PlaylistPortStub();
    }

    @Bean
    public BenutzerPort benutzerPort() {
        return new BenutzerPortStub();
    }

    @Bean
    public LiedPort liedPort() {
        return new LiedPortStub();
    }


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
