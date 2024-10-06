package de.acme.musicplayer.cucumber.stubtesting;

import de.acme.musicplayer.application.domain.BenutzerRegistrierenService;
import de.acme.musicplayer.application.domain.LiedAbspielenService;
import de.acme.musicplayer.application.ports.LiedLadenPort;
import de.acme.musicplayer.application.usecases.BenutzerRegistrierenUsecase;
import de.acme.musicplayer.application.usecases.LiedAbspielenUseCase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class StubConfiguration {

//        @Bean
//        public WebClient getWebClient(final WebClient.Builder builder) {
//            return builder.baseUrl("http://localhost").build();
//        }

    @Bean
    public LiedAbspielenUseCase playSongUseCase() {
        return new LiedAbspielenService(new LiedLadenPortStub());
    }

    @Bean
    public BenutzerRegistrierenUsecase benutzerRegistrierenUsecase() {
        return new BenutzerRegistrierenService(new BenutzerHinzufügenPortStub());
    }

    @Bean
    public LiedLadenPort liedLadenPort() {
        return new LiedLadenPortStub();
    }
}
