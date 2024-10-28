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
import de.acme.musicplayer.applications.scoreboard.domain.ScoreBoardAdministrationService;
import de.acme.musicplayer.applications.scoreboard.domain.ZähleNeueLieder;
import de.acme.musicplayer.applications.scoreboard.ports.ScoreboardEventPublisher;
import de.acme.musicplayer.applications.scoreboard.ports.UserScoreBoardPort;
import de.acme.musicplayer.applications.scoreboard.usecases.ScoreBoardAdministrationUsecase;
import de.acme.musicplayer.applications.scoreboard.usecases.ZähleNeueLiederUsecase;
import de.acme.musicplayer.applications.users.domain.AuszeichnungFürNeueTopScorerService;
import de.acme.musicplayer.applications.users.domain.BenutzerAdministrationService;
import de.acme.musicplayer.applications.users.domain.BenutzerRegistrierenService;
import de.acme.musicplayer.applications.users.ports.BenutzerPort;
import de.acme.musicplayer.applications.users.ports.UserEventPublisher;
import de.acme.musicplayer.applications.users.usecases.AuszeichnungFürNeueTopScorer;
import de.acme.musicplayer.applications.users.usecases.BenutzerAdministrationUsecase;
import de.acme.musicplayer.applications.users.usecases.BenutzerRegistrierenUsecase;
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
    public BenutzerRegistrierenUsecase benutzerRegistrierenUsecase(BenutzerPort benutzerPort) {
        return new BenutzerRegistrierenService(benutzerPort);
    }

    @Bean
    @Primary
    public BenutzerAdministrationUsecase benutzerAdministrationUsecase(BenutzerPort benutzerPort) {
        return new BenutzerAdministrationService(benutzerPort);
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

    @Primary
    @Bean
    public ScoreBoardAdministrationUsecase scoreBoardAdministrationUsecase(UserScoreBoardPort userScoreBoardPort) {
        return new ScoreBoardAdministrationService(userScoreBoardPort);
    }

    @Bean
    @Primary
    public ZähleNeueLiederUsecase zähleNeueLiederUsecase(UserScoreBoardPort userScoreBoardPort, ScoreboardEventPublisher scoreboardEventPublisher) {
        return new ZähleNeueLieder(userScoreBoardPort, scoreboardEventPublisher);
    }

    @Bean
    @Primary
    public AuszeichnungFürNeueTopScorer auszeichnungFürNeueTopScorer(BenutzerPort benutzerPort, UserEventPublisher userEventPublisher) {
        return new AuszeichnungFürNeueTopScorerService(benutzerPort, userEventPublisher);
    }
}
