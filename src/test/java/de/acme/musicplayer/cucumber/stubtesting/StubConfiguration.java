package de.acme.musicplayer.cucumber.stubtesting;

import de.acme.musicplayer.application.domain.BenutzerRegistrierenService;
import de.acme.musicplayer.application.domain.LiedAbspielenService;
import de.acme.musicplayer.application.domain.LiedHochladenService;
import de.acme.musicplayer.application.ports.BenutzerHinzufügenPort;
import de.acme.musicplayer.application.ports.LiedLadenPort;
import de.acme.musicplayer.application.usecases.BenutzerRegistrierenUsecase;
import de.acme.musicplayer.application.usecases.LiedAbspielenUseCase;
import de.acme.musicplayer.application.usecases.LiedHochladenUseCase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class StubConfiguration {

    @Bean
    public LiedAbspielenUseCase playSongUseCase(LiedPortStub liedPortStub) {
        return new LiedAbspielenService(liedPortStub);
    }

    @Bean
    public BenutzerRegistrierenUsecase benutzerRegistrierenUsecase(BenutzerHinzufügenPort benutzerHinzufügenPort) {
        return new BenutzerRegistrierenService(benutzerHinzufügenPort);
    }

    @Bean
    public BenutzerHinzufügenPort benutzerHinzufügenPort() {
        return new BenutzerHinzufügenPortStub();
    }

    @Bean
    public LiedPortStub liedPortStub() {
        return new LiedPortStub();
    }

    @Bean
    public LiedLadenPort liedLadenPort(LiedPortStub liedPortStub) {
        return liedPortStub;
    }

    @Bean
    public LiedHochladenUseCase liedHochladenUseCase(LiedPortStub liedPortStub) {
        return new LiedHochladenService(liedPortStub);
    }
}
