package de.acme.musicplayer.components.users.adapters.events;

import com.google.common.collect.EvictingQueue;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.common.events.Event;
import de.acme.musicplayer.common.events.EventPublisher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserEventPublisherFake implements EventPublisher {

    private final EvictingQueue<Event> events = EvictingQueue.create(1024);

    @Override
    public void publishEvent(Event event, TenantId tenantId) {
        events.add(event);
        log.info("Event wird nicht behandelt: {}", event);
    }

    @Override
    public void removeAllEventsFromOutboxByTenantId(TenantId tenantId) {
        events.removeIf(event -> event.getTenant().equals(tenantId));
    }
}
