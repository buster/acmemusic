package de.acme.musicplayer.cucumber.musicplayer.test2test;

import de.acme.musicplayer.applications.musicplayer.adapters.events.MusicplayerEventPublisherStub;
import de.acme.musicplayer.applications.musicplayer.adapters.jdbc.lied.LiedPortStub;
import de.acme.musicplayer.applications.musicplayer.domain.LiedAbspielenService;
import de.acme.musicplayer.applications.musicplayer.domain.LiedAdministrationService;
import de.acme.musicplayer.applications.musicplayer.domain.LiedHochladenService;
import de.acme.musicplayer.applications.musicplayer.ports.LiedPort;
import de.acme.musicplayer.applications.musicplayer.ports.MusicplayerEventPublisher;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedAbspielenUsecase;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedAdministrationUsecase;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedHochladenUsecase;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@CucumberContextConfiguration
public class CucumberT2TConfiguration {

    @TestConfiguration
    static class T2TConfiguration {

        // Adapter
        @Bean
        public LiedPort liedPort() {
            return new LiedPortStub();
        }

        @Bean
        public LiedAbspielenUsecase playSongUseCase(LiedPort liedPort) {
            return new LiedAbspielenService(liedPort);
        }

        @Bean
        public LiedAdministrationUsecase liedAdministrationUsecase(LiedPort liedPort, MusicplayerEventPublisher musicplayerEventPublisher) {
            return new LiedAdministrationService(liedPort, musicplayerEventPublisher);
        }

        @Bean
        public LiedHochladenUsecase liedHochladenUseCase(LiedPort liedPort, MusicplayerEventPublisher musicplayerEventPublisher) {
            return new LiedHochladenService(liedPort, musicplayerEventPublisher);
        }

        @Bean
        public MusicplayerEventPublisher MusicplayereventPublisher() {
            return new MusicplayerEventPublisherStub();
        }

    }
}