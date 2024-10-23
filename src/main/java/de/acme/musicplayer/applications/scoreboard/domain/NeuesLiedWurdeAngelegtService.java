package de.acme.musicplayer.applications.scoreboard.domain;

import de.acme.musicplayer.applications.scoreboard.ports.ScoreboardEventPublisher;
import de.acme.musicplayer.applications.scoreboard.ports.UserScoreBoardRepository;
import de.acme.musicplayer.applications.scoreboard.usecases.NeuesLiedWurdeAngelegtUsecase;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.events.NeuerTopScorerEvent;
import de.acme.musicplayer.events.NeuesLiedWurdeAngelegt;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NeuesLiedWurdeAngelegtService implements NeuesLiedWurdeAngelegtUsecase {

    private final UserScoreBoardRepository userScoreBoardRepository;
    private final ScoreboardEventPublisher scoreboardEventPublisher;

    public NeuesLiedWurdeAngelegtService(UserScoreBoardRepository userScoreBoardRepository, ScoreboardEventPublisher scoreboardEventPublisher) {
        this.userScoreBoardRepository = userScoreBoardRepository;
        this.scoreboardEventPublisher = scoreboardEventPublisher;
    }

    @Override
    public void neuesLiedWurdeAngelegt(NeuesLiedWurdeAngelegt event) {
        log.info("Received event: {}", event);
        Benutzer.Id aktuellerTopScorer = userScoreBoardRepository.höchstePunktZahl();
        userScoreBoardRepository.zähleNeuesLied(event.besitzerId());
        Benutzer.Id neuerTopScorer = userScoreBoardRepository.höchstePunktZahl();

        if (!neuerTopScorer.equals(aktuellerTopScorer)) {
            log.info("New top scorer: {}", neuerTopScorer);
            scoreboardEventPublisher.publishEvent(new NeuerTopScorerEvent(neuerTopScorer, event.getTenant()));
        }
    }
}
