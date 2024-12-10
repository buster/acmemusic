package de.acme.musicplayer.common.events;

import de.acme.musicplayer.ModuleApi;

@ModuleApi
public interface EventDispatcher {
    void handleEvent(Event event);

}
