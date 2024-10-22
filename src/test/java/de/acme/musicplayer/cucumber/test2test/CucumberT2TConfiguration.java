package de.acme.musicplayer.cucumber.test2test;

import de.acme.musicplayer.applications.musicplayer.adapters.events.MusicplayerEventPublisherStub;
import de.acme.musicplayer.applications.musicplayer.adapters.jdbc.lied.LiedPortStub;
import de.acme.musicplayer.applications.musicplayer.adapters.jdbc.playlist.PlaylistPortStub;
import de.acme.musicplayer.applications.musicplayer.domain.*;
import de.acme.musicplayer.applications.musicplayer.ports.LiedPort;
import de.acme.musicplayer.applications.musicplayer.ports.MusicplayerEventPublisher;
import de.acme.musicplayer.applications.musicplayer.ports.PlaylistPort;
import de.acme.musicplayer.applications.musicplayer.usecases.*;
import de.acme.musicplayer.applications.scoreboard.adapters.events.ScoreboardMusicplayerEventPublisherStub;
import de.acme.musicplayer.applications.scoreboard.adapters.jdbc.userscoreboard.UserScoreBoardPortStub;
import de.acme.musicplayer.applications.scoreboard.domain.NeuesLiedWurdeAngelegtService;
import de.acme.musicplayer.applications.scoreboard.domain.ScoreBoardAdministrationService;
import de.acme.musicplayer.applications.scoreboard.ports.ScoreboardEventPublisher;
import de.acme.musicplayer.applications.scoreboard.ports.UserScoreBoardPort;
import de.acme.musicplayer.applications.scoreboard.usecases.NeuesLiedWurdeAngelegtUsecase;
import de.acme.musicplayer.applications.scoreboard.usecases.ScoreBoardAdministrationUsecase;
import de.acme.musicplayer.applications.users.adapters.events.UserEventPublisherStub;
import de.acme.musicplayer.applications.users.adapters.jdbc.benutzer.BenutzerPortStub;
import de.acme.musicplayer.applications.users.domain.BenutzerAdministrationService;
import de.acme.musicplayer.applications.users.domain.BenutzerIstTopScorerService;
import de.acme.musicplayer.applications.users.domain.BenutzerRegistrierenService;
import de.acme.musicplayer.applications.users.ports.BenutzerPort;
import de.acme.musicplayer.applications.users.usecases.BenutzerAdministrationUsecase;
import de.acme.musicplayer.applications.users.usecases.BenutzerIstTopScorerUsecase;
import de.acme.musicplayer.applications.users.usecases.BenutzerRegistrierenUsecase;
import de.acme.musicplayer.applications.users.usecases.UserEventPublisher;
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
        public PlaylistPort playlistPort() {
            return new PlaylistPortStub();
        }

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
        public PlaylistAnlegenUsecase playlistAnlegenUsecase(PlaylistPort playlistPort) {
            return new PlaylistAnlegenService(playlistPort);
        }

        @Bean
        public PlaylistAdministrationUsecase playlistAdministrationUsecase(PlaylistPort playlistPort) {
            return new PlaylistAdministrationService(playlistPort);
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
        public LiedZuPlaylistHinzufügenUsecase liedZuPlaylistHinzufügenUseCase(PlaylistPort playlistPort) {
            return new LiedZuPlaylistHinzufügenService(playlistPort);
        }

        @Bean
        public LiedHochladenUsecase liedHochladenUseCase(LiedPort liedPort, @Qualifier("MusicplayereventPublisher") MusicplayerEventPublisher musicplayerEventPublisher) {
            return new LiedHochladenService(liedPort, musicplayerEventPublisher);
        }

        @Bean
        public LiederInPlaylistAuflistenUsecase liederInPlaylistAuflistenUseCase(PlaylistPort playlistPort) {
            return new LiederInPlaylistAuflistenService(playlistPort);
        }

        @Bean
        public NeuesLiedWurdeAngelegtUsecase neuesLiedWurdeAngelegtUsecase(UserScoreBoardPort userScoreBoardPort, ScoreboardEventPublisher scoreboardEventPublisher) {
            return new NeuesLiedWurdeAngelegtService(userScoreBoardPort, scoreboardEventPublisher);
        }

        @Bean
        public BenutzerIstTopScorerUsecase benutzerIstTopScorerUsecase(BenutzerPort benutzerPort, UserEventPublisher userEventPublisher) {
            return new BenutzerIstTopScorerService(benutzerPort, userEventPublisher);
        }

        @Bean
        public MusicplayerEventPublisher MusicplayereventPublisher(NeuesLiedWurdeAngelegtUsecase neuesLiedWurdeAngelegtUsecase) {
            return new MusicplayerEventPublisherStub(neuesLiedWurdeAngelegtUsecase);
        }

        @Bean
        public ScoreboardEventPublisher ScoreboardeventPublisher(BenutzerIstTopScorerUsecase benutzerIstTopScorerUsecase) {
            return new ScoreboardMusicplayerEventPublisherStub(benutzerIstTopScorerUsecase);
        }

        @Bean
        public UserEventPublisher userEventPublisher() {
            return new UserEventPublisherStub();
        }
    }
}
