package de.acme.musicplayer.componenttests.users.test2test;

import de.acme.musicplayer.common.events.EventPublisher;
import de.acme.musicplayer.components.users.adapters.events.UserEventPublisherFake;
import de.acme.musicplayer.components.users.adapters.jdbc.benutzer.BenutzerPortFake;
import de.acme.musicplayer.components.users.domain.BenutzerAdministrationService;
import de.acme.musicplayer.components.users.domain.BenutzerRegistrierenService;
import de.acme.musicplayer.components.users.domain.BenutzerWurdeNeuerTopScorerService;
import de.acme.musicplayer.components.users.domain.UserEventDispatcherImpl;
import de.acme.musicplayer.components.users.ports.BenutzerPort;
import de.acme.musicplayer.components.users.usecases.BenutzerAdministrationUsecase;
import de.acme.musicplayer.components.users.usecases.BenutzerRegistrierenUsecase;
import de.acme.musicplayer.components.users.usecases.BenutzerWurdeNeuerTopScorer;
import de.acme.musicplayer.components.users.usecases.UserEventDispatcher;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@CucumberContextConfiguration
public class CucumberT2TConfiguration {

    @TestConfiguration
    static class T2TConfiguration {

        // Adapter
        @Bean
        public BenutzerPort benutzerPort() {
            return new BenutzerPortFake();
        }

        @Bean
        public BenutzerRegistrierenUsecase benutzerRegistrierenUsecase(BenutzerPort benutzerPort) {
            return new BenutzerRegistrierenService(benutzerPort);
        }

        @Bean
        public BenutzerAdministrationUsecase benutzerAdministrationUsecase(BenutzerPort benutzerPort, @Qualifier("userEventPublisher")
        EventPublisher userEventPublisher) {
            return new BenutzerAdministrationService(benutzerPort, userEventPublisher);
        }

        @Bean
        public BenutzerWurdeNeuerTopScorer benutzerIstTopScorerUsecase(BenutzerPort benutzerPort, @Qualifier("userEventPublisher")
        EventPublisher userEventPublisher) {
            return new BenutzerWurdeNeuerTopScorerService(benutzerPort, userEventPublisher);
        }

        @Bean("userEventPublisher")
        public EventPublisher userEventPublisher() {
            return new UserEventPublisherFake();
        }

        @Bean
        public UserEventDispatcher userEventDispatcher(BenutzerWurdeNeuerTopScorer benutzerWurdeNeuerTopScorer) {
            return new UserEventDispatcherImpl(benutzerWurdeNeuerTopScorer);
        }
    }
}
