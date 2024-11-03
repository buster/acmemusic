package de.acme.musicplayer;

import de.acme.musicplayer.applications.musicplayer.domain.LiedAbspielenService;
import de.acme.musicplayer.applications.musicplayer.domain.LiedAdministrationService;
import de.acme.musicplayer.applications.musicplayer.domain.LiedHochladenService;
import de.acme.musicplayer.applications.musicplayer.domain.LiederAuflistenService;
import de.acme.musicplayer.applications.musicplayer.ports.LiedPort;
import de.acme.musicplayer.applications.musicplayer.ports.MusicplayerEventPublisher;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedAbspielenUsecase;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedAdministrationUsecase;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedHochladenUsecase;
import de.acme.musicplayer.applications.musicplayer.usecases.LiederAuflistenUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AcmeConfiguration {

    @Bean
    @Primary
    public LiedAbspielenUsecase liedAbspielenUsecase(LiedPort liedPort) {
        return new LiedAbspielenService(liedPort);
    }

    @Bean
    @Primary
    public LiedAdministrationUsecase liedAdministrationUsecase(LiedPort liedPort) {
        return new LiedAdministrationService(liedPort);
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
