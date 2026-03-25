package de.acme.musicplayer.components.scoreboard.adapters.events;

import de.acme.musicplayer.common.events.Event;
import de.acme.musicplayer.common.events.EventDispatcher;
import de.acme.musicplayer.components.musicplayer.domain.events.NeuesLiedWurdeAngelegt;
import de.acme.musicplayer.components.scoreboard.usecases.ZaehleNeueLiederUsecase;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ScoreBoardEventListeners implements EventDispatcher {
    private final ZaehleNeueLiederUsecase zähleNeueLiederUsecase;

    public ScoreBoardEventListeners(ZaehleNeueLiederUsecase zähleNeueLiederUsecase) {
        this.zähleNeueLiederUsecase = zähleNeueLiederUsecase;
    }

    @Override
    @EventListener
    public void handleEvent(Event event) {
        // Synchrone Verarbeitung erforderlich: Die Event-Kette (NeuesLiedWurdeAngelegt → Score-Update
        // → BenutzerIstNeuerTopScorer → Auszeichnung → SSE) muss transaktional konsistent ablaufen.
        // @Async würde dazu führen, dass Downstream-Listener Events empfangen bevor der Score persistiert ist.
        if (event instanceof NeuesLiedWurdeAngelegt neuesLiedWurdeAngelegt) {
            zähleNeueLiederUsecase.zähleNeueAngelegteLieder(neuesLiedWurdeAngelegt);
        }
    }

}
