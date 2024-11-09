package de.acme.musicplayer.components.musicplayer.adapters.events;

import com.google.common.collect.EvictingQueue;
import de.acme.musicplayer.common.TenantId;
import de.acme.musicplayer.common.events.Event;
import de.acme.musicplayer.components.musicplayer.ports.MusicplayerEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpringMusicplayerEventPublisher implements MusicplayerEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final EvictingQueue<Event> eventQueue = EvictingQueue.create(1024);

    public SpringMusicplayerEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public void publishEvent(Event event) {
        applicationEventPublisher.publishEvent(event);
    }

    @Override
    public List<Event> readEvents(int maxEvents) {
        return eventQueue.stream().limit(maxEvents).toList();
    }

    @Override
    public void removeEvents(List<Event> events) {
        eventQueue.removeAll(events);

    }

    @Override
    public void removeEventsByTenantId(TenantId tenantId) {
        eventQueue.removeIf(event -> event.getTenant().equals(tenantId));
    }
}
