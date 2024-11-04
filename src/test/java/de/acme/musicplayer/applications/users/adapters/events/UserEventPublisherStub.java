package de.acme.musicplayer.applications.users.adapters.events;

import com.google.common.collect.EvictingQueue;
import de.acme.musicplayer.applications.users.ports.UserEventPublisher;
import de.acme.musicplayer.common.Event;
import de.acme.musicplayer.common.TenantId;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class UserEventPublisherStub implements UserEventPublisher {

    private final EvictingQueue<Event> events = EvictingQueue.create(1024);

    @Override
    public void publishEvent(Event event) {
        events.add(event);
        log.info("Event wird nicht behandelt: {}", event);
    }

    @Override
    public List<Event> readEvents(int maxEvents) {
        return events.stream().limit(maxEvents).toList();
    }

    @Override
    public void removeEvents(List<Event> events) {
        this.events.removeAll(events);

    }

    @Override
    public void removeEventsByTenantId(TenantId tenantId) {
        events.removeIf(event -> event.getTenant().equals(tenantId));
    }
}
