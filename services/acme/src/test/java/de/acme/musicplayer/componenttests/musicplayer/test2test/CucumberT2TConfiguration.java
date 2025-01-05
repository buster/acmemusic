package de.acme.musicplayer.componenttests.musicplayer.test2test;

import de.acme.musicplayer.common.events.EventPublisher;
import de.acme.musicplayer.components.musicplayer.adapters.events.MusicplayerEventPublisherFake;
import de.acme.musicplayer.components.musicplayer.adapters.jdbc.lied.LiedPortFake;
import de.acme.musicplayer.components.musicplayer.domain.LiedAbspielenService;
import de.acme.musicplayer.components.musicplayer.domain.LiedAdministrationService;
import de.acme.musicplayer.components.musicplayer.domain.LiedHochladenService;
import de.acme.musicplayer.components.musicplayer.ports.LiedPort;
import de.acme.musicplayer.components.musicplayer.usecases.LiedAbspielenUsecase;
import de.acme.musicplayer.components.musicplayer.usecases.LiedAdministrationUsecase;
import de.acme.musicplayer.components.musicplayer.usecases.LiedHochladenUsecase;
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
            return new LiedPortFake();
        }

        @Bean
        public LiedAbspielenUsecase playSongUseCase(LiedPort liedPort) {
            return new LiedAbspielenService(liedPort);
        }

        @Bean
        public LiedAdministrationUsecase liedAdministrationUsecase(LiedPort liedPort, EventPublisher musicplayerEventPublisher) {
            return new LiedAdministrationService(liedPort, musicplayerEventPublisher);
        }

        @Bean
        public LiedHochladenUsecase liedHochladenUseCase(LiedPort liedPort, EventPublisher musicplayerEventPublisher) {
            return new LiedHochladenService(liedPort, musicplayerEventPublisher);
        }

        @Bean
        public EventPublisher musicplayereventPublisher() {
            return new MusicplayerEventPublisherFake();
        }
    }
}
