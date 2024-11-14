package de.acme.musicplayer.common.api;

import de.acme.support.ModuleApi;

import static com.google.common.base.Preconditions.checkNotNull;

@ModuleApi
public record BenutzerId(String Id) {
    public BenutzerId {
        checkNotNull(Id);
    }
}
