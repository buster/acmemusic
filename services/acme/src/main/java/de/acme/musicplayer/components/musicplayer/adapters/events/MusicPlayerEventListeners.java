package de.acme.musicplayer.components.musicplayer.adapters.events;

import de.acme.musicplayer.components.musicplayer.domain.events.NeuesLiedWurdeAngelegt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Component
@Slf4j
public class MusicPlayerEventListeners {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handleNeuesLiedWurdeAngelegt(NeuesLiedWurdeAngelegt event) {
        log.info("Received event: " + event);
    }

}
