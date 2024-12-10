package de.acme.musicplayer.common.events;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.common.TenantId;

@ModuleApi
public interface Event {
    TenantId getTenant();

}
