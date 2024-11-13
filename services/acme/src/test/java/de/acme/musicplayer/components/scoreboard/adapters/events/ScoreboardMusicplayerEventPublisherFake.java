package de.acme.musicplayer.components.scoreboard.adapters.events;

import de.acme.musicplayer.common.TenantId;
import de.acme.musicplayer.common.events.Event;
import de.acme.musicplayer.common.events.EventPublisher;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardMusicplayerEventPublisherFake implements EventPublisher {

    private final List<Event> events = new ArrayList<>();

    @Override
    public void publishEvent(Event event, TenantId tenantId) {
        events.add(event);
    }

    @Override
    public List<Event> readEventsFromOutbox(int maxEvents, TenantId tenantId) {
        return events
                .stream()
                .filter(event -> event.getTenant().equals(tenantId))
                .toList()
                .subList(0, Math.min(events.size(), maxEvents));
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
