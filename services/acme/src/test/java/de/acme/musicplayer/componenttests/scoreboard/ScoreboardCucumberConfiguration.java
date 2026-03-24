package de.acme.musicplayer.componenttests.scoreboard;

import de.acme.musicplayer.common.events.EventPublisher;
import de.acme.musicplayer.components.scoreboard.adapters.events.ScoreboardMusicplayerEventPublisherFake;
import de.acme.musicplayer.components.scoreboard.adapters.jdbc.userscoreboard.UserScoreBoardPortFake;
import de.acme.musicplayer.components.scoreboard.domain.ScoreBoardAdministrationService;
import de.acme.musicplayer.components.scoreboard.domain.ZaehleNeueLieder;
import de.acme.musicplayer.components.scoreboard.ports.UserScoreBoardPort;
import de.acme.musicplayer.components.scoreboard.usecases.ScoreBoardAdministrationUsecase;
import de.acme.musicplayer.components.scoreboard.usecases.ZaehleNeueLiederUsecase;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@CucumberContextConfiguration
public class ScoreboardCucumberConfiguration {

    @TestConfiguration
    static class ScoreboardTestConfiguration {

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
        public ZaehleNeueLiederUsecase neuesLiedWurdeAngelegtUsecase(UserScoreBoardPort userScoreBoardPort, EventPublisher scoreboardEventPublisher) {
            return new ZaehleNeueLieder(userScoreBoardPort, scoreboardEventPublisher);
        }

        @Bean
        public EventPublisher scoreboardEventPublisher() {
            return new ScoreboardMusicplayerEventPublisherFake();
        }
    }
}
