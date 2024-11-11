package de.acme.musicplayer.componenttests.scoreboard.test2test;

import de.acme.musicplayer.common.events.EventPublisher;
import de.acme.musicplayer.components.scoreboard.adapters.events.ScoreboardMusicplayerEventPublisherFake;
import de.acme.musicplayer.components.scoreboard.adapters.jdbc.userscoreboard.UserScoreBoardPortFake;
import de.acme.musicplayer.components.scoreboard.domain.ScoreBoardAdministrationService;
import de.acme.musicplayer.components.scoreboard.domain.ZähleNeueLieder;
import de.acme.musicplayer.components.scoreboard.ports.UserScoreBoardPort;
import de.acme.musicplayer.components.scoreboard.usecases.ScoreBoardAdministrationUsecase;
import de.acme.musicplayer.components.scoreboard.usecases.ZähleNeueLiederUsecase;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@CucumberContextConfiguration
public class CucumberT2TConfiguration {

    @TestConfiguration
    static class T2TConfiguration {

        // Adapter
        @Bean
        public UserScoreBoardPort userScoreBoardRepository() {
            return new UserScoreBoardPortFake();
        }

        @Bean
        public ScoreBoardAdministrationUsecase scoreBoardAdministrationUsecase(UserScoreBoardPort userScoreBoardPort, EventPublisher scoreboardEventPublisher) {
            return new ScoreBoardAdministrationService(userScoreBoardPort, scoreboardEventPublisher);
        }

        @Bean
        public ZähleNeueLiederUsecase neuesLiedWurdeAngelegtUsecase(UserScoreBoardPort userScoreBoardPort, EventPublisher scoreboardEventPublisher) {
            return new ZähleNeueLieder(userScoreBoardPort, scoreboardEventPublisher);
        }

        @Bean
        public EventPublisher scoreboardEventPublisher() {
            return new ScoreboardMusicplayerEventPublisherFake();
        }
    }
}
