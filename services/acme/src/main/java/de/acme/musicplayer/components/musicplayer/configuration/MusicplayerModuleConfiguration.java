package de.acme.musicplayer.components.musicplayer.configuration;

import de.acme.musicplayer.common.events.EventPublisher;
import de.acme.musicplayer.components.musicplayer.domain.LiedAbspielenService;
import de.acme.musicplayer.components.musicplayer.domain.LiedAdministrationService;
import de.acme.musicplayer.components.musicplayer.domain.LiedHochladenService;
import de.acme.musicplayer.components.musicplayer.domain.LiederAuflistenService;
import de.acme.musicplayer.components.musicplayer.ports.LiedPort;
import de.acme.musicplayer.components.musicplayer.usecases.LiedAbspielenUsecase;
import de.acme.musicplayer.components.musicplayer.usecases.LiedAdministrationUsecase;
import de.acme.musicplayer.components.musicplayer.usecases.LiedHochladenUsecase;
import de.acme.musicplayer.components.musicplayer.usecases.LiederAuflistenUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class MusicplayerModuleConfiguration {

    @Bean
    @Primary
    public LiedAbspielenUsecase liedAbspielenUsecase(LiedPort liedPort) {
        return new LiedAbspielenService(liedPort);
    }

    @Bean
    @Primary
    public LiedAdministrationUsecase liedAdministrationUsecase(LiedPort liedPort, EventPublisher musicplayerEventPublisher) {
        return new LiedAdministrationService(liedPort, musicplayerEventPublisher);
    }

    @Bean
    @Primary
    public LiedHochladenUsecase liedHochladenUseCase(LiedPort liedPort, EventPublisher musicplayerEventPublisher) {
        return new LiedHochladenService(liedPort, musicplayerEventPublisher);
    }

    @Bean
    @Primary
    LiederAuflistenUsecase liederAuflistenUsecase(LiedPort liedPort) {
        return new LiederAuflistenService(liedPort);
    }
}
