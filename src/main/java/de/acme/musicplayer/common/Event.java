package de.acme.musicplayer.common;

import de.acme.musicplayer.ModuleApi;

@ModuleApi
public interface Event {
    TenantId getTenant();

}
