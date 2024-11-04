package de.acme.musicplayer.applications.scoreboard.domain;

import de.acme.musicplayer.applications.musicplayer.domain.events.NeuesLiedWurdeAngelegt;
import de.acme.musicplayer.applications.scoreboard.usecases.ScoreboardEventDispatcher;
import de.acme.musicplayer.applications.scoreboard.usecases.ZähleNeueLiederUsecase;
import de.acme.musicplayer.common.Event;

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
