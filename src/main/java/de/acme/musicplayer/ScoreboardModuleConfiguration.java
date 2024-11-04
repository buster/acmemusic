package de.acme.musicplayer;

import de.acme.musicplayer.applications.scoreboard.domain.ScoreBoardAdministrationService;
import de.acme.musicplayer.applications.scoreboard.domain.ScoreboardEventDispatcherImpl;
import de.acme.musicplayer.applications.scoreboard.domain.ZähleNeueLieder;
import de.acme.musicplayer.applications.scoreboard.ports.ScoreboardEventPublisher;
import de.acme.musicplayer.applications.scoreboard.ports.UserScoreBoardPort;
import de.acme.musicplayer.applications.scoreboard.usecases.ScoreBoardAdministrationUsecase;
import de.acme.musicplayer.applications.scoreboard.usecases.ScoreboardEventDispatcher;
import de.acme.musicplayer.applications.scoreboard.usecases.ZähleNeueLiederUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class ScoreboardModuleConfiguration {

    @Primary
    @Bean
    public ScoreBoardAdministrationUsecase scoreBoardAdministrationUsecase(UserScoreBoardPort userScoreBoardPort, ScoreboardEventPublisher scoreboardEventPublisher) {
        return new ScoreBoardAdministrationService(userScoreBoardPort, scoreboardEventPublisher);
    }

    @Primary
    @Bean
    public ScoreboardEventDispatcher scoreboardEventDispatcher(ZähleNeueLiederUsecase zähleNeueLiederUsecase) {
        return new ScoreboardEventDispatcherImpl(zähleNeueLiederUsecase);
    }

    @Bean
    @Primary
    public ZähleNeueLiederUsecase zähleNeueLiederUsecase(UserScoreBoardPort userScoreBoardPort, ScoreboardEventPublisher scoreboardEventPublisher) {
        return new ZähleNeueLieder(userScoreBoardPort, scoreboardEventPublisher);
    }
}
