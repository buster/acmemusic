package de.acme.musicplayer.components.musicplayer.domain.events;

import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.LiedId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.common.events.Event;
import de.acme.support.ModuleApi;

@ModuleApi
public record NeuesLiedWurdeAngelegt(LiedId liedId, BenutzerId besitzerId,
                                     TenantId tenantId)
        implements Event {

    @Override
    public TenantId getTenant() {
        return tenantId;
    }
}
