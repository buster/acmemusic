package de.acme.musicplayer.components.users.configuration;

import de.acme.musicplayer.common.events.EventPublisher;
import de.acme.musicplayer.components.users.domain.BenutzerAdministrationService;
import de.acme.musicplayer.components.users.domain.BenutzerRegistrierenService;
import de.acme.musicplayer.components.users.domain.BenutzerWurdeNeuerTopScorerService;
import de.acme.musicplayer.components.users.ports.BenutzerPort;
import de.acme.musicplayer.components.users.usecases.BenutzerAdministrationUsecase;
import de.acme.musicplayer.components.users.usecases.BenutzerRegistrierenUsecase;
import de.acme.musicplayer.components.users.usecases.BenutzerWurdeNeuerTopScorer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class UsersModuleConfiguration {

    @Bean
    @Primary
    public BenutzerRegistrierenUsecase benutzerRegistrierenUsecase(BenutzerPort benutzerPort) {
        return new BenutzerRegistrierenService(benutzerPort);
    }

    @Bean
    @Primary
    public BenutzerAdministrationUsecase benutzerAdministrationUsecase(BenutzerPort benutzerPort, EventPublisher userEventPublisher) {
        return new BenutzerAdministrationService(benutzerPort, userEventPublisher);
    }

    @Bean
    @Primary
    public BenutzerWurdeNeuerTopScorer auszeichnungFürNeueTopScorer(BenutzerPort benutzerPort, EventPublisher userEventPublisher) {
        return new BenutzerWurdeNeuerTopScorerService(benutzerPort, userEventPublisher);
    }
}
