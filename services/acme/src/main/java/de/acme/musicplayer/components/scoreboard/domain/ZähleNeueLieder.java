package de.acme.musicplayer.components.scoreboard.domain;

import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.events.EventPublisher;
import de.acme.musicplayer.components.musicplayer.domain.events.NeuesLiedWurdeAngelegt;
import de.acme.musicplayer.components.scoreboard.domain.events.BenutzerIstNeuerTopScorer;
import de.acme.musicplayer.components.scoreboard.ports.UserScoreBoardPort;
import de.acme.musicplayer.components.scoreboard.usecases.ZähleNeueLiederUsecase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class ZähleNeueLieder implements ZähleNeueLiederUsecase {

    private final UserScoreBoardPort userScoreBoardPort;
    private final EventPublisher scoreboardEventPublisher;

    public ZähleNeueLieder(UserScoreBoardPort userScoreBoardPort, EventPublisher scoreboardEventPublisher) {
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
            BenutzerIstNeuerTopScorer benutzerIstNeuerTopScorer = new BenutzerIstNeuerTopScorer(neuerTopScorer, aktuellerTopScorer, event.getTenant());
            scoreboardEventPublisher.publishEvent(benutzerIstNeuerTopScorer, event.getTenant());
        }
    }
}
