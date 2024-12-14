package de.acme.musicplayer.components.musicplayer.configuration;

import de.acme.musicplayer.common.events.EventPublisher;
import de.acme.musicplayer.common.events.SpringApplicationEventPublisher;
import de.acme.musicplayer.components.musicplayer.domain.*;
import de.acme.musicplayer.components.musicplayer.ports.LiedPort;
import de.acme.musicplayer.components.musicplayer.usecases.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class MusicplayerModuleConfiguration {

    @Bean
    @Primary
    public MusicplayerEventDispatcher musicplayerEventDispatcher() {
        return new MusicplayerEventDispatcherImpl();
    }

    @Bean
    @Primary
    public LiedAbspielenUsecase liedAbspielenUsecase(LiedPort liedPort) {
        return new LiedAbspielenService(liedPort);
    }

    @Bean("musicplayerEventPublisher")
    @Primary
    public EventPublisher musicplayerEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new SpringApplicationEventPublisher(applicationEventPublisher);
    }

    @Bean
    @Primary
    public LiedAdministrationUsecase liedAdministrationUsecase(LiedPort liedPort, @Qualifier("musicplayerEventPublisher") EventPublisher musicplayerEventPublisher) {
        return new LiedAdministrationService(liedPort, musicplayerEventPublisher);
    }

    @Bean
    @Primary
    public LiedHochladenUsecase liedHochladenUseCase(LiedPort liedPort, @Qualifier("musicplayerEventPublisher") EventPublisher musicplayerEventPublisher) {
        return new LiedHochladenService(liedPort, musicplayerEventPublisher);
    }

    @Bean
    @Primary
    LiederAuflistenUsecase liederAuflistenUsecase(LiedPort liedPort) {
        return new LiederAuflistenService(liedPort);
    }
}
