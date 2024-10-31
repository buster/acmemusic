package de.acme.musicplayer.common;

import de.acme.musicplayer.ModuleApi;

import java.util.List;

@ModuleApi
public interface EventPublisher {
    void publishEvent(Event event);

    List<Event> readEvents(int maxEvents);

    void removeEvents(List<Event> events);

    void removeEventsByTenantId(TenantId tenantId);
}
