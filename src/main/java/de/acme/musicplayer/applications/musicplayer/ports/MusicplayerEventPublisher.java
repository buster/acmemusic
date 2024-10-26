package de.acme.musicplayer.applications.musicplayer.ports;

import de.acme.musicplayer.common.Event;

public interface MusicplayerEventPublisher {
    void publishEvent(Event event);

}
