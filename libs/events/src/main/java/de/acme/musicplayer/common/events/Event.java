package de.acme.musicplayer.common.events;


import de.acme.musicplayer.common.api.TenantId;
import de.acme.support.ModuleApi;

@ModuleApi
public interface Event {
    TenantId getTenant();

}
