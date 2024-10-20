package de.acme.musicplayer.events;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;

import static de.acme.musicplayer.applications.musicplayer.domain.model.Lied.*;

@ModuleApi
public record NeuesLiedWurdeAngelegt(Id id, String titel, de.acme.musicplayer.applications.users.domain.model.Benutzer.Id besitzerId,
                                     de.acme.musicplayer.applications.musicplayer.domain.model.TenantId tenantId)
        implements Event {

    @Override
    public TenantId getTenant() {
        return tenantId;
    }
}
