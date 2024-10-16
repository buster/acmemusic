package de.acme.musicplayer.applications.musicplayer.domain.model;

import de.acme.musicplayer.ModuleApi;

@ModuleApi
public record TenantId(String value) {
    public TenantId() {
        this("GLOBAL");
    }
}
