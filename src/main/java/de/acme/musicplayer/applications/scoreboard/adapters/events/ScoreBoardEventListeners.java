package de.acme.musicplayer.applications.scoreboard.adapters.events;

import de.acme.musicplayer.applications.musicplayer.domain.events.NeuesLiedWurdeAngelegt;
import de.acme.musicplayer.applications.scoreboard.usecases.ZähleNeueLiederUsecase;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ScoreBoardEventListeners {

    private final ZähleNeueLiederUsecase zähleNeueLiederUsecase;

    public ScoreBoardEventListeners(ZähleNeueLiederUsecase zähleNeueLiederUsecase) {
        this.zähleNeueLiederUsecase = zähleNeueLiederUsecase;
    }

    @EventListener
    public void handleLiedAngelegt(NeuesLiedWurdeAngelegt event) {
        zähleNeueLiederUsecase.zähleNeueAngelegteLieder(event);

    }

}
