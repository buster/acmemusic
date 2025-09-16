package de.acme.musicplayer.common.events;

import com.google.common.collect.EvictingQueue;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.support.ModuleApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@ModuleApi
@Slf4j
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
    public void removeAllEventsFromOutboxByTenantId(TenantId tenantId) {
        events.removeIf(event -> event.getTenant().equals(tenantId));
    }
}
