package de.acme.musicplayer;

import de.acme.musicplayer.applications.musicplayer.domain.*;
import de.acme.musicplayer.applications.musicplayer.ports.LiedPort;
import de.acme.musicplayer.applications.musicplayer.ports.MusicplayerEventPublisher;
import de.acme.musicplayer.applications.musicplayer.ports.PlaylistPort;
import de.acme.musicplayer.applications.musicplayer.usecases.*;
import de.acme.musicplayer.applications.scoreboard.domain.NeuesLiedWurdeAngelegtService;
import de.acme.musicplayer.applications.scoreboard.domain.ScoreBoardAdministrationService;
import de.acme.musicplayer.applications.scoreboard.ports.ScoreboardEventPublisher;
import de.acme.musicplayer.applications.scoreboard.ports.UserScoreBoardPort;
import de.acme.musicplayer.applications.scoreboard.usecases.NeuesLiedWurdeAngelegtUsecase;
import de.acme.musicplayer.applications.scoreboard.usecases.ScoreBoardAdministrationUsecase;
import de.acme.musicplayer.applications.users.domain.BenutzerAdministrationService;
import de.acme.musicplayer.applications.users.domain.BenutzerIstTopScorerService;
import de.acme.musicplayer.applications.users.domain.BenutzerRegistrierenService;
import de.acme.musicplayer.applications.users.ports.BenutzerPort;
import de.acme.musicplayer.applications.users.usecases.BenutzerAdministrationUsecase;
import de.acme.musicplayer.applications.users.usecases.BenutzerIstTopScorerUsecase;
import de.acme.musicplayer.applications.users.usecases.BenutzerRegistrierenUsecase;
import de.acme.musicplayer.applications.users.usecases.UserEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AcmeConfiguration {

    @Bean
    public LiedAbspielenUsecase liedAbspielenUsecase(LiedPort liedPort) {
        return new LiedAbspielenService(liedPort);
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
    public LiedZuPlaylistHinzufügenUsecase liedZuPlaylistHinzufügenUseCase(PlaylistPort playlistPort) {
        return new LiedZuPlaylistHinzufügenService(playlistPort);
    }

    @Bean
    public LiedHochladenUsecase liedHochladenUseCase(LiedPort liedPort, MusicplayerEventPublisher musicplayerEventPublisher) {
        return new LiedHochladenService(liedPort, musicplayerEventPublisher);
    }

    @Bean
    public LiederInPlaylistAuflistenUsecase liederInPlaylistAuflistenUseCase(PlaylistPort playlistPort) {
        return new LiederInPlaylistAuflistenService(playlistPort);
    }

    @Bean
    LiederAuflistenUsecase liederAuflistenUsecase(LiedPort liedPort) {
        return new LiederAuflistenService(liedPort);
    }

    @Bean
    public ScoreBoardAdministrationUsecase scoreBoardAdministrationUsecase(UserScoreBoardPort userScoreBoardPort) {
        return new ScoreBoardAdministrationService(userScoreBoardPort);
    }

    @Bean
    public NeuesLiedWurdeAngelegtUsecase neuesLiedWurdeAngelegtUsecase(UserScoreBoardPort userScoreBoardPort, ScoreboardEventPublisher scoreboardEventPublisher) {
        return new NeuesLiedWurdeAngelegtService(userScoreBoardPort, scoreboardEventPublisher);
    }

    @Bean
    public BenutzerIstTopScorerUsecase benutzerIstTopScorerUsecase(BenutzerPort benutzerPort, UserEventPublisher userEventPublisher) {
        return new BenutzerIstTopScorerService(benutzerPort, userEventPublisher);
    }
}
