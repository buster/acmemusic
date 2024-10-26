package de.acme.musicplayer.applications.musicplayer.adapters.events;

import de.acme.musicplayer.applications.musicplayer.domain.events.NeuesLiedWurdeAngelegt;
import de.acme.musicplayer.applications.musicplayer.ports.MusicplayerEventPublisher;
import de.acme.musicplayer.applications.scoreboard.usecases.ZähleNeueLiederUsecase;
import de.acme.musicplayer.common.Event;

public class MusicplayerEventPublisherStub implements MusicplayerEventPublisher {

    private final ZähleNeueLiederUsecase zähleNeueLiederUsecase;

    public MusicplayerEventPublisherStub(ZähleNeueLiederUsecase zähleNeueLiederUsecase) {
        this.zähleNeueLiederUsecase = zähleNeueLiederUsecase;
    }


    @Override
    public void publishEvent(Event event) {
        if (event instanceof NeuesLiedWurdeAngelegt) {
            zähleNeueLiederUsecase.zähleNeueAngelegteLieder((NeuesLiedWurdeAngelegt) event);
        } else {
            throw new IllegalArgumentException("Unknown event type: " + event.getClass().getName());
        }
    }
}
