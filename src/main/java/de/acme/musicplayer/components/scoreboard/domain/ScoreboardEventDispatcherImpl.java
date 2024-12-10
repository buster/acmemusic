package de.acme.musicplayer.components.scoreboard.domain;

import de.acme.musicplayer.common.events.Event;
import de.acme.musicplayer.components.musicplayer.domain.events.NeuesLiedWurdeAngelegt;
import de.acme.musicplayer.components.scoreboard.usecases.ScoreboardEventDispatcher;
import de.acme.musicplayer.components.scoreboard.usecases.ZähleNeueLiederUsecase;

public class ScoreboardEventDispatcherImpl implements ScoreboardEventDispatcher {
    private final ZähleNeueLiederUsecase zähleNeueLiederUsecase;

    public ScoreboardEventDispatcherImpl(ZähleNeueLiederUsecase zähleNeueLiederUsecase) {
        this.zähleNeueLiederUsecase = zähleNeueLiederUsecase;
    }

    @Override
    public void handleEvent(Event event) {
        if (event instanceof NeuesLiedWurdeAngelegt neuesLiedWurdeAngelegt) {
            zähleNeueLiederUsecase.zähleNeueAngelegteLieder(neuesLiedWurdeAngelegt);
        }
    }
}
