package de.acme.musicplayer.common.events;

import com.google.common.collect.EvictingQueue;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.support.ModuleApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ModuleApi
@Slf4j
public class SpringApplicationEventPublisher implements EventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final EvictingQueue<Event> events = EvictingQueue.create(1024);
    private final SpringApplicationEventPublisherProperties springApplicationEventPublisherProperties;

    public SpringApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher,
                                           SpringApplicationEventPublisherProperties springApplicationEventPublisherProperties) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.springApplicationEventPublisherProperties = springApplicationEventPublisherProperties;
    }


    @Override
    public void publishEvent(Event event, TenantId tenantId) {
        if (springApplicationEventPublisherProperties.isSaveToOutbox()) {
            events.add(event);
        } else {
            log.info("skipping event saving to outbox");
        }
        if (springApplicationEventPublisherProperties.isPublishEventsToApplication()) {
            applicationEventPublisher.publishEvent(event);
        } else {
            log.info("skipping event publishing to application");
        }
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
