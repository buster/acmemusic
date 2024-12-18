package de.acme.musicplayer.common.events;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.common.TenantId;

import java.util.List;

@ModuleApi
public interface EventPublisher {
    void publishEvent(Event event, TenantId tenantId);

    List<Event> readEventsFromOutbox(int maxEvents, TenantId tenantId);

    void removeEventsFromOutbox(List<Event> events);

    void removeAllEventsFromOutboxByTenantId(TenantId tenantId);
}
