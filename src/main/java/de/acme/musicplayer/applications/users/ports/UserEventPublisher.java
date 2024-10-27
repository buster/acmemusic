package de.acme.musicplayer.applications.users.ports;

import de.acme.musicplayer.common.Event;

public interface UserEventPublisher {
    void publishEvent(Event event);

}
