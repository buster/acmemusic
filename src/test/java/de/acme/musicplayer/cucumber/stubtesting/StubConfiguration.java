package de.acme.musicplayer.cucumber.stubtesting;

import de.acme.musicplayer.application.domain.PlaySongService;
import de.acme.musicplayer.application.usecases.PlaySongUseCase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class StubConfiguration {

//        @Bean
//        public WebClient getWebClient(final WebClient.Builder builder) {
//            return builder.baseUrl("http://localhost").build();
//        }

    @Bean
    public PlaySongUseCase playSongUseCase() {
        return new PlaySongService(new LoadSongPortStub());
    }
}
