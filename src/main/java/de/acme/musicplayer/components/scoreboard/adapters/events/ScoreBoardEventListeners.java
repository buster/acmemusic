package de.acme.musicplayer.components.scoreboard.adapters.events;

import de.acme.musicplayer.common.events.Event;
import de.acme.musicplayer.common.events.EventDispatcher;
import de.acme.musicplayer.components.musicplayer.domain.events.NeuesLiedWurdeAngelegt;
import de.acme.musicplayer.components.scoreboard.usecases.ZähleNeueLiederUsecase;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ScoreBoardEventListeners implements EventDispatcher {
    private final ZähleNeueLiederUsecase zähleNeueLiederUsecase;

    public ScoreBoardEventListeners(ZähleNeueLiederUsecase zähleNeueLiederUsecase) {
        this.zähleNeueLiederUsecase = zähleNeueLiederUsecase;
    }

    @Override
    @EventListener
    @Async
    public void handleEvent(Event event) {
        if (event instanceof NeuesLiedWurdeAngelegt neuesLiedWurdeAngelegt) {
            zähleNeueLiederUsecase.zähleNeueAngelegteLieder(neuesLiedWurdeAngelegt);
        }
    }

}
