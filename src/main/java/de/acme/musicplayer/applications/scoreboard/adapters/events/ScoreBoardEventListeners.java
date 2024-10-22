package de.acme.musicplayer.applications.scoreboard.adapters.events;

import de.acme.musicplayer.applications.scoreboard.usecases.NeuesLiedWurdeAngelegtUsecase;
import de.acme.musicplayer.events.NeuesLiedWurdeAngelegt;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ScoreBoardEventListeners {

    private final NeuesLiedWurdeAngelegtUsecase neuesLiedWurdeAngelegtUsecase;

    public ScoreBoardEventListeners(NeuesLiedWurdeAngelegtUsecase neuesLiedWurdeAngelegtUsecase) {
        this.neuesLiedWurdeAngelegtUsecase = neuesLiedWurdeAngelegtUsecase;
    }

    @EventListener
    public void handleLiedAngelegt(NeuesLiedWurdeAngelegt event) {
        neuesLiedWurdeAngelegtUsecase.neuesLiedWurdeAngelegt(event);

    }

}
