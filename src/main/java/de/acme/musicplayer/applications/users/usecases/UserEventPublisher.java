package de.acme.musicplayer.applications.users.usecases;

import de.acme.musicplayer.events.Event;

public interface UserEventPublisher {
    void publishEvent(Event event);

}
