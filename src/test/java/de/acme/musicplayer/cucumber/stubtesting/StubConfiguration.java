package de.acme.musicplayer.cucumber.stubtesting;

import de.acme.musicplayer.application.domain.*;
import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.ports.BenutzerPort;
import de.acme.musicplayer.application.ports.LiedPort;
import de.acme.musicplayer.application.ports.PlaylistPort;
import de.acme.musicplayer.application.usecases.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class StubConfiguration {

    @Bean
    public LiedAbspielenUseCase playSongUseCase(LiedPortStub liedPortStub) {
        return new LiedAbspielenService(liedPortStub);
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
    public PlaylistPort playlistPort() {
        return new PlaylistPortStub();
    }

    @Bean
    public LiedZuPlaylistHinzufügenUseCase liedZuPlaylistHinzufügenUseCase(PlaylistPort playlistPort) {
        return new LiedZuPlaylistHinzufügenService(playlistPort);
    }

    @Bean
    public BenutzerPort benutzerPort() {
        return new BenutzerPortStub();
    }

    @Bean
    public LiedPortStub liedPortStub() {
        return new LiedPortStub();
    }

    @Bean
    public LiedHochladenUseCase liedHochladenUseCase(LiedPortStub liedPortStub) {
        return new LiedHochladenService(liedPortStub);
    }

    @Bean
    public LiederInPlaylistAuflistenUsecase liederInPlaylistAuflistenUseCase(PlaylistPort playlistPort) {
        return new LiederInPlaylistAuflistenService(playlistPort);
    }
}
