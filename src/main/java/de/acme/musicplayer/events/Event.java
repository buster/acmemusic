package de.acme.musicplayer.events;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;

@ModuleApi
public interface Event {
    TenantId getTenant();
}
