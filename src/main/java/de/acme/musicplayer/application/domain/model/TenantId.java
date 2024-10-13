package de.acme.musicplayer.application.domain.model;

public record TenantId(String value) {
    public TenantId() {
        this("GLOBAL");
    }
}
