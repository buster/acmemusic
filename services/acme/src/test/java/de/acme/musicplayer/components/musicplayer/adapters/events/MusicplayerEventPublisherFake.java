package de.acme.musicplayer.components.musicplayer.adapters.events;

import com.google.common.collect.EvictingQueue;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.common.events.Event;
import de.acme.musicplayer.common.events.EventPublisher;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MusicplayerEventPublisherFake implements EventPublisher {

    private final EvictingQueue<Event> events = EvictingQueue.create(1024);

    @Override
    public void publishEvent(Event event, TenantId tenantId) {
        events.add(event);
        log.info("Event wird nicht behandelt: {}", event);
    }

    @Override
    public List<Event> readEventsFromOutbox(int maxEvents, TenantId tenantId) {
        return events.stream()
                .filter(event -> event.getTenant().equals(tenantId))
                .limit(maxEvents).toList();
    }

    @Override
    public void removeEventsFromOutbox(List<Event> events) {
        this.events.removeAll(events);

    }

    @Override
    public void removeAllEventsFromOutboxByTenantId(TenantId tenantId) {
        events.removeIf(event -> event.getTenant().equals(tenantId));
    }
}
