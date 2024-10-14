package de.acme.musicplayer.applications.musicplayer.domain.model;

public record TenantId(String value) {
    public TenantId() {
        this("GLOBAL");
    }
}
