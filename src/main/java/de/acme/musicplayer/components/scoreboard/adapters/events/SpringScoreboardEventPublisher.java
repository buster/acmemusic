package de.acme.musicplayer.components.scoreboard.adapters.events;

import com.google.common.collect.EvictingQueue;
import de.acme.musicplayer.common.TenantId;
import de.acme.musicplayer.common.events.Event;
import de.acme.musicplayer.components.scoreboard.ports.ScoreboardEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpringScoreboardEventPublisher implements ScoreboardEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final EvictingQueue<Event> events = EvictingQueue.create(1024);

    public SpringScoreboardEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public void publishEvent(Event event) {
        events.add(event);
        applicationEventPublisher.publishEvent(event);
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
