package de.acme.musicplayer.applications.scoreboard.domain;

import de.acme.musicplayer.applications.scoreboard.ports.ScoreboardEventPublisher;
import de.acme.musicplayer.applications.scoreboard.ports.UserScoreBoardPort;
import de.acme.musicplayer.applications.scoreboard.usecases.NeuesLiedWurdeAngelegtUsecase;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.events.NeuerTopScorerEvent;
import de.acme.musicplayer.events.NeuesLiedWurdeAngelegt;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NeuesLiedWurdeAngelegtService implements NeuesLiedWurdeAngelegtUsecase {

    private final UserScoreBoardPort userScoreBoardPort;
    private final ScoreboardEventPublisher scoreboardEventPublisher;

    public NeuesLiedWurdeAngelegtService(UserScoreBoardPort userScoreBoardPort, ScoreboardEventPublisher scoreboardEventPublisher) {
        this.userScoreBoardPort = userScoreBoardPort;
        this.scoreboardEventPublisher = scoreboardEventPublisher;
    }

    @Override
    public void neuesLiedWurdeAngelegt(NeuesLiedWurdeAngelegt event) {
        log.info("Received event: {}", event);
        Benutzer.Id aktuellerTopScorer = userScoreBoardPort.höchstePunktZahl(event.getTenant());
        userScoreBoardPort.zähleNeuesLied(event.besitzerId(), event.getTenant());
        Benutzer.Id neuerTopScorer = userScoreBoardPort.höchstePunktZahl(event.getTenant());

        if (!neuerTopScorer.equals(aktuellerTopScorer)) {
            log.info("New top scorer: {}", neuerTopScorer);
            scoreboardEventPublisher.publishEvent(new NeuerTopScorerEvent(neuerTopScorer, aktuellerTopScorer, event.getTenant()));
        }
    }
}
