package de.acme.musicplayer.components.musicplayer.domain.events;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.TenantId;
import de.acme.musicplayer.common.events.Event;

@ModuleApi
public record NeuesLiedWurdeAngelegt(LiedId liedId, BenutzerId besitzerId,
                                     TenantId tenantId)
        implements Event {

    @Override
    public TenantId getTenant() {
        return tenantId;
    }
}
