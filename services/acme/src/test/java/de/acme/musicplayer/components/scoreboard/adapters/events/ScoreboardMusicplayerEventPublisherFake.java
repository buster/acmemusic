package de.acme.musicplayer.components.scoreboard.adapters.events;

import de.acme.musicplayer.common.api.TenantId;
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
    public void removeAllEventsFromOutboxByTenantId(TenantId tenantId) {
        events.removeIf(event -> event.getTenant().equals(tenantId));
    }
}
