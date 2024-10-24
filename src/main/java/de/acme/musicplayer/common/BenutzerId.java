package de.acme.musicplayer.common;

import de.acme.musicplayer.ModuleApi;

import static com.google.common.base.Preconditions.checkNotNull;

@ModuleApi
public record BenutzerId(String Id) {
    public BenutzerId {
        checkNotNull(Id);
    }
}
