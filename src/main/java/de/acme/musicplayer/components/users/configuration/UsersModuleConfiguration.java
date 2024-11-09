package de.acme.musicplayer.components.users.configuration;

import de.acme.musicplayer.components.users.domain.AuszeichnungFürNeueTopScorerService;
import de.acme.musicplayer.components.users.domain.BenutzerAdministrationService;
import de.acme.musicplayer.components.users.domain.BenutzerRegistrierenService;
import de.acme.musicplayer.components.users.domain.UserEventDispatcherImpl;
import de.acme.musicplayer.components.users.ports.BenutzerPort;
import de.acme.musicplayer.components.users.ports.UserEventPublisher;
import de.acme.musicplayer.components.users.usecases.AuszeichnungFürNeueTopScorer;
import de.acme.musicplayer.components.users.usecases.BenutzerAdministrationUsecase;
import de.acme.musicplayer.components.users.usecases.BenutzerRegistrierenUsecase;
import de.acme.musicplayer.components.users.usecases.UserEventDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class UsersModuleConfiguration {

    @Bean
    @Primary
    public UserEventDispatcher userEventDispatcher(AuszeichnungFürNeueTopScorer auszeichnungFürNeueTopScorer) {
        return new UserEventDispatcherImpl(auszeichnungFürNeueTopScorer);
    }


    @Bean
    @Primary
    public BenutzerRegistrierenUsecase benutzerRegistrierenUsecase(BenutzerPort benutzerPort) {
        return new BenutzerRegistrierenService(benutzerPort);
    }

    @Bean
    @Primary
    public BenutzerAdministrationUsecase benutzerAdministrationUsecase(BenutzerPort benutzerPort, UserEventPublisher userEventPublisher) {
        return new BenutzerAdministrationService(benutzerPort, userEventPublisher);
    }

    @Bean
    @Primary
    public AuszeichnungFürNeueTopScorer auszeichnungFürNeueTopScorer(BenutzerPort benutzerPort, UserEventPublisher userEventPublisher) {
        return new AuszeichnungFürNeueTopScorerService(benutzerPort, userEventPublisher);
    }
}
