package de.acme.musicplayer.common.api;


import de.acme.support.ModuleApi;

@ModuleApi
public record TenantId(String value) {
    public TenantId {
        java.util.Objects.requireNonNull(value, "TenantId value must not be null");
    }
}
