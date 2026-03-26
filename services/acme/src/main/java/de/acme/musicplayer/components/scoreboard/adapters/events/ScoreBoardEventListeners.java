package de.acme.musicplayer.components.scoreboard.adapters.events;

import de.acme.musicplayer.components.musicplayer.domain.events.NeuesLiedWurdeAngelegt;
import de.acme.musicplayer.components.scoreboard.usecases.ZaehleNeueLiederUsecase;
import de.acme.support.ModuleApi;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@ModuleApi
public class ScoreBoardEventListeners {

    private final ZaehleNeueLiederUsecase zähleNeueLiederUsecase;

    public ScoreBoardEventListeners(ZaehleNeueLiederUsecase zähleNeueLiederUsecase) {
        this.zähleNeueLiederUsecase = zähleNeueLiederUsecase;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void onNeuesLiedWurdeAngelegt(NeuesLiedWurdeAngelegt event) {
        zähleNeueLiederUsecase.zähleNeueAngelegteLieder(event);
    }
}
