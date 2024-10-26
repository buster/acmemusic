package de.acme.musicplayer.applications.users.adapters.events;

import de.acme.musicplayer.applications.users.usecases.UserEventPublisher;
import de.acme.musicplayer.common.Event;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserEventPublisherStub implements UserEventPublisher {
    @Override
    public void publishEvent(Event event) {
        log.info("Event wird nicht behandelt: {}", event);
    }
}
