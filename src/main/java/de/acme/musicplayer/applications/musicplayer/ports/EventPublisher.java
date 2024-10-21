package de.acme.musicplayer.applications.musicplayer.ports;

import de.acme.musicplayer.events.Event;

public interface EventPublisher {
    void publishEvent(Event event);

}
