package de.acme.musicplayer.applications.musicplayer.adapters.events;

import de.acme.musicplayer.applications.scoreboard.usecases.NeuesLiedWurdeAngelegtUsecase;
import de.acme.musicplayer.events.Event;
import de.acme.musicplayer.events.NeuesLiedWurdeAngelegt;
import de.acme.musicplayer.applications.musicplayer.ports.EventPublisher;

public class DirectEventPublisherStub implements EventPublisher {

    private final NeuesLiedWurdeAngelegtUsecase neuesLiedWurdeAngelegtUsecase;

    public DirectEventPublisherStub(NeuesLiedWurdeAngelegtUsecase neuesLiedWurdeAngelegtUsecase) {
        this.neuesLiedWurdeAngelegtUsecase = neuesLiedWurdeAngelegtUsecase;
    }


    @Override
    public void publishEvent(Event event) {
        if (event instanceof NeuesLiedWurdeAngelegt) {
            neuesLiedWurdeAngelegtUsecase.neuesLiedWurdeAngelegt((NeuesLiedWurdeAngelegt) event);
        } else {
            throw new IllegalArgumentException("Unknown event type: " + event.getClass().getName());
        }
    }
}
