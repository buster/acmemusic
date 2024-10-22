package de.acme.musicplayer.applications.users.adapters.events;

import de.acme.musicplayer.applications.users.usecases.UserEventPublisher;
import de.acme.musicplayer.events.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpringUserEventPublisher implements UserEventPublisher {
    @Override
    public void publishEvent(Event event) {
        log.info("Event wird nicht behandelt: {}", event);
    }
}
