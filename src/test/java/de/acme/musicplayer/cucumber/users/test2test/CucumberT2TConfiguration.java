package de.acme.musicplayer.cucumber.users.test2test;

import de.acme.musicplayer.applications.users.adapters.events.UserEventPublisherStub;
import de.acme.musicplayer.applications.users.adapters.jdbc.benutzer.BenutzerPortStub;
import de.acme.musicplayer.applications.users.domain.AuszeichnungFürNeueTopScorerService;
import de.acme.musicplayer.applications.users.domain.BenutzerAdministrationService;
import de.acme.musicplayer.applications.users.domain.BenutzerRegistrierenService;
import de.acme.musicplayer.applications.users.ports.BenutzerPort;
import de.acme.musicplayer.applications.users.ports.UserEventPublisher;
import de.acme.musicplayer.applications.users.usecases.AuszeichnungFürNeueTopScorer;
import de.acme.musicplayer.applications.users.usecases.BenutzerAdministrationUsecase;
import de.acme.musicplayer.applications.users.usecases.BenutzerRegistrierenUsecase;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@CucumberContextConfiguration
public class CucumberT2TConfiguration {

    @TestConfiguration
    static class T2TConfiguration {

        // Adapter
        @Bean
        public BenutzerPort benutzerPort() {
            return new BenutzerPortStub();
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
        public AuszeichnungFürNeueTopScorer benutzerIstTopScorerUsecase(BenutzerPort benutzerPort, UserEventPublisher userEventPublisher) {
            return new AuszeichnungFürNeueTopScorerService(benutzerPort, userEventPublisher);
        }

        @Bean
        public UserEventPublisher userEventPublisher() {
            return new UserEventPublisherStub();
        }
    }
}
