package de.acme.musicplayer.common;

import de.acme.musicplayer.ModuleApi;

@ModuleApi
public
record TenantId(String value) {
    public TenantId() {
        this("GLOBAL");
    }

    @Override
    public String toString() {
        return "TenantId{" +
                "value='" + value + '\'' +
                '}';
    }
}
