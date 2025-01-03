package de.acme.musicplayer.components.scoreboard.configuration;

import de.acme.musicplayer.common.events.EventPublisher;
import de.acme.musicplayer.components.scoreboard.domain.ScoreBoardAdministrationService;
import de.acme.musicplayer.components.scoreboard.domain.ZähleNeueLieder;
import de.acme.musicplayer.components.scoreboard.ports.UserScoreBoardPort;
import de.acme.musicplayer.components.scoreboard.usecases.ScoreBoardAdministrationUsecase;
import de.acme.musicplayer.components.scoreboard.usecases.ZähleNeueLiederUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class ScoreboardModuleConfiguration {

    @Primary
    @Bean
    public ScoreBoardAdministrationUsecase scoreBoardAdministrationUsecase(UserScoreBoardPort userScoreBoardPort,
                                                                           EventPublisher scoreboardEventPublisher) {
        return new ScoreBoardAdministrationService(userScoreBoardPort, scoreboardEventPublisher);
    }

    @Bean
    @Primary
    public ZähleNeueLiederUsecase zähleNeueLiederUsecase(UserScoreBoardPort userScoreBoardPort,
                                                         EventPublisher scoreboardEventPublisher) {
        return new ZähleNeueLieder(userScoreBoardPort, scoreboardEventPublisher);
    }


}
