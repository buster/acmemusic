package de.acme.musicplayer.common;

import de.acme.musicplayer.ModuleApi;

import static com.google.common.base.Preconditions.checkNotNull;

@ModuleApi
public record LiedId(String id) {
    public LiedId {
        checkNotNull(id);
    }
}
