package de.acme.musicplayer.applications.musicplayer.domain.model;

import com.google.common.base.Objects;
import de.acme.musicplayer.ModuleApi;

@ModuleApi
public record TenantId(String value) {
    public TenantId() {
        this("GLOBAL");
    }
}
