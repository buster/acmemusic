package de.acme.musicplayer.events;

import de.acme.musicplayer.ModuleApi;

@ModuleApi
public interface Event {
    String getTenant();
}
