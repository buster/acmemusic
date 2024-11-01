package de.acme.musicplayer;

import de.acme.musicplayer.applications.musicplayer.domain.*;
import de.acme.musicplayer.applications.musicplayer.ports.LiedPort;
import de.acme.musicplayer.applications.musicplayer.ports.MusicplayerEventPublisher;
import de.acme.musicplayer.applications.musicplayer.usecases.*;
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

    @Bean
    @Primary
    public LiedAdministrationUsecase liedAdministrationUsecase(LiedPort liedPort, MusicplayerEventPublisher musicplayerEventPublisher) {
        return new LiedAdministrationService(liedPort, musicplayerEventPublisher);
    }

    @Bean
    @Primary
    public LiedHochladenUsecase liedHochladenUseCase(LiedPort liedPort, MusicplayerEventPublisher musicplayerEventPublisher) {
        return new LiedHochladenService(liedPort, musicplayerEventPublisher);
    }

    @Bean
    @Primary
    LiederAuflistenUsecase liederAuflistenUsecase(LiedPort liedPort) {
        return new LiederAuflistenService(liedPort);
    }
}
