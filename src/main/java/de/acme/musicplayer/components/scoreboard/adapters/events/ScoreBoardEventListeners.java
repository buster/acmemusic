package de.acme.musicplayer.components.scoreboard.adapters.events;

import de.acme.musicplayer.components.musicplayer.domain.events.NeuesLiedWurdeAngelegt;
import de.acme.musicplayer.components.scoreboard.usecases.ScoreboardEventDispatcher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ScoreBoardEventListeners {

    private final ScoreboardEventDispatcher scoreboardEventDispatcher;

    public ScoreBoardEventListeners(ScoreboardEventDispatcher scoreboardEventDispatcher) {
        this.scoreboardEventDispatcher = scoreboardEventDispatcher;
    }

    @EventListener
    public void handleLiedAngelegt(NeuesLiedWurdeAngelegt event) {
        scoreboardEventDispatcher.handleEvent(event);
    }
}
