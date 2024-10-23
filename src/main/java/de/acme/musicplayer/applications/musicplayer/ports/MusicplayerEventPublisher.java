package de.acme.musicplayer.applications.musicplayer.ports;

import de.acme.musicplayer.events.Event;

public interface MusicplayerEventPublisher {
    void publishEvent(Event event);

}
