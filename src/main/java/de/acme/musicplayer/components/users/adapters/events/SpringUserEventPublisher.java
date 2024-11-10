package de.acme.musicplayer.components.users.adapters.events;

import de.acme.musicplayer.common.TenantId;
import de.acme.musicplayer.common.events.Event;
import de.acme.musicplayer.components.users.ports.UserEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SpringUserEventPublisher implements UserEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public SpringUserEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public void publishEvent(Event event) {
        applicationEventPublisher.publishEvent(event);
    }

    @Override
    public List<Event> readEvents(int maxEvents) {
        return List.of();
    }

    @Override
    public void removeEvents(List<Event> events) {

    }

    @Override
    public void removeEventsByTenantId(TenantId tenantId) {

    }
}
