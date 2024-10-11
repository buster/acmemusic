package de.acme.musicplayer.cucumber.stubtesting.real2real.real2real;

import de.acme.musicplayer.adapters.jdbc.BenutzerRepository;
import de.acme.musicplayer.adapters.jdbc.LiedRepository;
import de.acme.musicplayer.adapters.jdbc.PlaylistRepository;
import de.acme.musicplayer.application.domain.*;
import de.acme.musicplayer.application.ports.BenutzerPort;
import de.acme.musicplayer.application.ports.LiedPort;
import de.acme.musicplayer.application.ports.PlaylistPort;
import de.acme.musicplayer.application.usecases.*;
import io.cucumber.spring.CucumberContextConfiguration;
import org.jooq.DSLContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class CucumberR2RConfiguration {


    @TestConfiguration
    public static class T2RConfiguration {

        // BEGIN: Adapter
        @Bean
        public PlaylistPort playlistPort(DSLContext dslContext) {
            return new PlaylistRepository(dslContext);
        }

        @Bean
        public BenutzerPort benutzerPort(DSLContext dslContext) {
            return new BenutzerRepository(dslContext);
        }

        @Bean
        public LiedPort liedPort(DSLContext dslContext) {
            return new LiedRepository(dslContext);
        }

        // END: Adapter

        @Bean
        public PlaylistAnlegenUsecase playlistAnlegenUsecase(PlaylistPort playlistPort) {
            return new PlaylistAnlegenService(playlistPort);
        }

        @Bean
        public PlaylistAdministrationUsecase playlistAdministrationUsecase(PlaylistPort playlistPort) {
            return new PlaylistAdministrationService(playlistPort);
        }

        @Bean
        public LiedAbspielenUsecase playSongUseCase(LiedPort liedPort) {
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
}
