package de.acme.musicplayer.common;

import java.util.List;

public interface EventPublisher {
    void publishEvent(Event event);

    List<Event> readEvents(int maxEvents);

    void removeEvents(List<Event> events);

    void removeEventsByTenantId(TenantId tenantId);
}
