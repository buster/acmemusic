package de.acme.musicplayer.applications.scoreboard.domain;

import de.acme.musicplayer.applications.musicplayer.domain.events.NeuesLiedWurdeAngelegt;
import de.acme.musicplayer.applications.scoreboard.domain.events.BenutzerIstNeuerTopScorer;
import de.acme.musicplayer.applications.scoreboard.ports.ScoreboardEventPublisher;
import de.acme.musicplayer.applications.scoreboard.ports.UserScoreBoardPort;
import de.acme.musicplayer.applications.scoreboard.usecases.ZähleNeueLiederUsecase;
import de.acme.musicplayer.common.BenutzerId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class ZähleNeueLieder implements ZähleNeueLiederUsecase {

    private final UserScoreBoardPort userScoreBoardPort;
    private final ScoreboardEventPublisher scoreboardEventPublisher;

    public ZähleNeueLieder(UserScoreBoardPort userScoreBoardPort, ScoreboardEventPublisher scoreboardEventPublisher) {
        this.userScoreBoardPort = userScoreBoardPort;
        this.scoreboardEventPublisher = scoreboardEventPublisher;
    }

    @Override
    @Transactional
    public void zähleNeueAngelegteLieder(NeuesLiedWurdeAngelegt event) {
        log.info("Received event: {}", event);
        BenutzerId aktuellerTopScorer = userScoreBoardPort.höchstePunktZahl(event.getTenant());
        userScoreBoardPort.zähleNeuesLied(event.besitzerId(), event.getTenant());
        BenutzerId neuerTopScorer = userScoreBoardPort.höchstePunktZahl(event.getTenant());

        if (!neuerTopScorer.equals(aktuellerTopScorer)) {
            log.info("New top scorer: {}", neuerTopScorer);
            scoreboardEventPublisher.publishEvent(new BenutzerIstNeuerTopScorer(neuerTopScorer, aktuellerTopScorer, event.getTenant()));
        }
    }
}
