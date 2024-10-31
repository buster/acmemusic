package de.acme.musicplayer.cucumber.scoreboard.test2test;

import de.acme.musicplayer.applications.scoreboard.adapters.events.ScoreboardMusicplayerEventPublisherStub;
import de.acme.musicplayer.applications.scoreboard.adapters.jdbc.userscoreboard.UserScoreBoardPortStub;
import de.acme.musicplayer.applications.scoreboard.domain.ScoreBoardAdministrationService;
import de.acme.musicplayer.applications.scoreboard.domain.ZähleNeueLieder;
import de.acme.musicplayer.applications.scoreboard.ports.ScoreboardEventPublisher;
import de.acme.musicplayer.applications.scoreboard.ports.UserScoreBoardPort;
import de.acme.musicplayer.applications.scoreboard.usecases.ScoreBoardAdministrationUsecase;
import de.acme.musicplayer.applications.scoreboard.usecases.ZähleNeueLiederUsecase;
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
            return new UserScoreBoardPortStub();
        }

        @Bean
        public ScoreBoardAdministrationUsecase scoreBoardAdministrationUsecase(UserScoreBoardPort userScoreBoardPort, ScoreboardEventPublisher scoreboardEventPublisher) {
            return new ScoreBoardAdministrationService(userScoreBoardPort, scoreboardEventPublisher);
        }

        @Bean
        public ZähleNeueLiederUsecase neuesLiedWurdeAngelegtUsecase(UserScoreBoardPort userScoreBoardPort, ScoreboardEventPublisher scoreboardEventPublisher) {
            return new ZähleNeueLieder(userScoreBoardPort, scoreboardEventPublisher);
        }

        @Bean
        public ScoreboardEventPublisher scoreboardEventPublisher() {
            return new ScoreboardMusicplayerEventPublisherStub();
        }
    }
}
