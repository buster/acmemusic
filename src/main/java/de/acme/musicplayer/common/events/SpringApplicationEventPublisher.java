package de.acme.musicplayer.common.events;

import com.google.common.collect.EvictingQueue;
import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.common.TenantId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ModuleApi
public class SpringApplicationEventPublisher implements EventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final EvictingQueue<Event> events = EvictingQueue.create(1024);

    public SpringApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public void publishEvent(Event event, TenantId tenantId) {
        events.add(event);
        applicationEventPublisher.publishEvent(event);
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
