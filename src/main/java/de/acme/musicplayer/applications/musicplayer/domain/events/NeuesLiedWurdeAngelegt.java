package de.acme.musicplayer.applications.musicplayer.domain.events;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.Event;
import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.TenantId;

@ModuleApi
public record NeuesLiedWurdeAngelegt(LiedId liedId, String titel, BenutzerId besitzerId,
                                     TenantId tenantId)
        implements Event {

    @Override
    public TenantId getTenant() {
        return tenantId;
    }
}
