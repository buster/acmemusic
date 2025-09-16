package de.acme.musicplayer.components.scoreboard.domain;

import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.events.EventPublisher;
import de.acme.musicplayer.components.musicplayer.domain.events.NeuesLiedWurdeAngelegt;
import de.acme.musicplayer.components.scoreboard.domain.events.BenutzerIstNeuerTopScorer;
import de.acme.musicplayer.components.scoreboard.ports.UserScoreBoardPort;
import de.acme.musicplayer.components.scoreboard.usecases.ZähleNeueLiederUsecase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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
        
        BenutzerId aktuellerTopScorer = userScoreBoardPort.findeBenutzerMitHöchsterPunktZahl(event.getTenant());
        userScoreBoardPort.zähleNeuesLied(event.besitzerId(), event.getTenant());
        BenutzerId neuerTopScorer = userScoreBoardPort.findeBenutzerMitHöchsterPunktZahl(event.getTenant());
        
        prüfeUndVeröffentlicheTopScorerÄnderung(aktuellerTopScorer, neuerTopScorer, event.getTenant());
    }
    
    private void prüfeUndVeröffentlicheTopScorerÄnderung(BenutzerId aktuellerTopScorer, BenutzerId neuerTopScorer, 
                                                        de.acme.musicplayer.common.api.TenantId tenant) {
        if (!Objects.equals(neuerTopScorer, aktuellerTopScorer)) {
            log.info("New top scorer: {}", neuerTopScorer);
            
            BenutzerIstNeuerTopScorer event = new BenutzerIstNeuerTopScorer(
                    neuerTopScorer, aktuellerTopScorer, tenant);
            scoreboardEventPublisher.publishEvent(event, tenant);
        }
    }
}
