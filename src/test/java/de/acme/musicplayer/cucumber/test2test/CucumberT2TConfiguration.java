package de.acme.musicplayer.cucumber.test2test;

import de.acme.musicplayer.applications.musicplayer.adapters.events.MusicplayerEventPublisherStub;
import de.acme.musicplayer.applications.musicplayer.adapters.jdbc.lied.LiedPortStub;
import de.acme.musicplayer.applications.musicplayer.domain.LiedAbspielenService;
import de.acme.musicplayer.applications.musicplayer.domain.LiedAdministrationService;
import de.acme.musicplayer.applications.musicplayer.domain.LiedHochladenService;
import de.acme.musicplayer.applications.musicplayer.ports.LiedPort;
import de.acme.musicplayer.applications.musicplayer.ports.MusicplayerEventPublisher;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedAbspielenUsecase;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedAdministrationUsecase;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedHochladenUsecase;
import de.acme.musicplayer.applications.scoreboard.adapters.events.ScoreboardMusicplayerEventPublisherStub;
import de.acme.musicplayer.applications.scoreboard.adapters.jdbc.userscoreboard.UserScoreBoardPortStub;
import de.acme.musicplayer.applications.scoreboard.domain.ScoreBoardAdministrationService;
import de.acme.musicplayer.applications.scoreboard.domain.ZähleNeueLieder;
import de.acme.musicplayer.applications.scoreboard.ports.ScoreboardEventPublisher;
import de.acme.musicplayer.applications.scoreboard.ports.UserScoreBoardPort;
import de.acme.musicplayer.applications.scoreboard.usecases.ScoreBoardAdministrationUsecase;
import de.acme.musicplayer.applications.scoreboard.usecases.ZähleNeueLiederUsecase;
import de.acme.musicplayer.applications.users.adapters.events.UserEventPublisherStub;
import de.acme.musicplayer.applications.users.adapters.jdbc.benutzer.BenutzerPortStub;
import de.acme.musicplayer.applications.users.domain.AuszeichnungFürNeueTopScorerService;
import de.acme.musicplayer.applications.users.domain.BenutzerAdministrationService;
import de.acme.musicplayer.applications.users.domain.BenutzerRegistrierenService;
import de.acme.musicplayer.applications.users.ports.BenutzerPort;
import de.acme.musicplayer.applications.users.ports.UserEventPublisher;
import de.acme.musicplayer.applications.users.usecases.AuszeichnungFürNeueTopScorer;
import de.acme.musicplayer.applications.users.usecases.BenutzerAdministrationUsecase;
import de.acme.musicplayer.applications.users.usecases.BenutzerRegistrierenUsecase;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@CucumberContextConfiguration
public class CucumberT2TConfiguration {

    @TestConfiguration
    static class T2TConfiguration {

        // Adapter
        @Bean
        public BenutzerPort benutzerPort() {
            return new BenutzerPortStub();
        }

        @Bean
        public LiedPort liedPort() {
            return new LiedPortStub();
        }

        @Bean
        public UserScoreBoardPort userScoreBoardRepository() {
            return new UserScoreBoardPortStub();
        }

        @Bean
        public LiedAbspielenUsecase playSongUseCase(LiedPort liedPort) {
            return new LiedAbspielenService(liedPort);
        }

        @Bean
        public BenutzerRegistrierenUsecase benutzerRegistrierenUsecase(BenutzerPort benutzerPort) {
            return new BenutzerRegistrierenService(benutzerPort);
        }

        @Bean
        public BenutzerAdministrationUsecase benutzerAdministrationUsecase(BenutzerPort benutzerPort) {
            return new BenutzerAdministrationService(benutzerPort);
        }

        @Bean
        public LiedAdministrationUsecase liedAdministrationUsecase(LiedPort liedPort) {
            return new LiedAdministrationService(liedPort);
        }

        @Bean
        public ScoreBoardAdministrationUsecase scoreBoardAdministrationUsecase(UserScoreBoardPort userScoreBoardPort) {
            return new ScoreBoardAdministrationService(userScoreBoardPort);
        }

        @Bean
        public LiedHochladenUsecase liedHochladenUseCase(LiedPort liedPort, @Qualifier("MusicplayereventPublisher") MusicplayerEventPublisher musicplayerEventPublisher) {
            return new LiedHochladenService(liedPort, musicplayerEventPublisher);
        }

        @Bean
        public ZähleNeueLiederUsecase neuesLiedWurdeAngelegtUsecase(UserScoreBoardPort userScoreBoardPort, ScoreboardEventPublisher scoreboardEventPublisher) {
            return new ZähleNeueLieder(userScoreBoardPort, scoreboardEventPublisher);
        }

        @Bean
        public AuszeichnungFürNeueTopScorer benutzerIstTopScorerUsecase(BenutzerPort benutzerPort, UserEventPublisher userEventPublisher) {
            return new AuszeichnungFürNeueTopScorerService(benutzerPort, userEventPublisher);
        }

        @Bean
        public MusicplayerEventPublisher MusicplayereventPublisher(ZähleNeueLiederUsecase zähleNeueLiederUsecase) {
            return new MusicplayerEventPublisherStub(zähleNeueLiederUsecase);
        }

        @Bean
        public ScoreboardEventPublisher scoreboardEventPublisher(AuszeichnungFürNeueTopScorer auszeichnungFürNeueTopScorer) {
            return new ScoreboardMusicplayerEventPublisherStub(auszeichnungFürNeueTopScorer);
        }

        @Bean
        public UserEventPublisher userEventPublisher() {
            return new UserEventPublisherStub();
        }
    }
}
