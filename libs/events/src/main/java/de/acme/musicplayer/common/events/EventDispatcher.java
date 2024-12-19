package de.acme.musicplayer.common.events;

import de.acme.support.ModuleApi;

@ModuleApi
public interface EventDispatcher {
    void handleEvent(Event event);

}
