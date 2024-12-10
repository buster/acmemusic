package de.acme.musicplayer.components.scoreboard.adapters.events;

import de.acme.musicplayer.common.TenantId;
import de.acme.musicplayer.common.events.Event;
import de.acme.musicplayer.components.scoreboard.ports.ScoreboardEventPublisher;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardMusicplayerEventPublisherFake implements ScoreboardEventPublisher {

    private final List<Event> events = new ArrayList<>();

    @Override
    public void publishEvent(Event event) {
        events.add(event);
    }

    @Override
    public List<Event> readEvents(int maxEvents) {
        return events.subList(0, Math.min(events.size(), maxEvents));
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
