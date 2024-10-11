package de.acme.musicplayer.cucumber.stubtesting.test2real;

import de.acme.musicplayer.adapters.jdbc.BenutzerRepository;
import de.acme.musicplayer.adapters.jdbc.PlaylistRepository;
import de.acme.musicplayer.adapters.jdbc.SongRepository;
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
@SpringBootTest
@DirtiesContext
public class CucumberT2RConfiguration {

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
            return new SongRepository(dslContext);
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
}
