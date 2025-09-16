package de.acme.musicplayer.common.events;

import de.acme.musicplayer.common.api.TenantId;
import de.acme.support.ModuleApi;

@ModuleApi
public interface EventPublisher {
    void publishEvent(Event event, TenantId tenantId);

    void removeAllEventsFromOutboxByTenantId(TenantId tenantId);
}
