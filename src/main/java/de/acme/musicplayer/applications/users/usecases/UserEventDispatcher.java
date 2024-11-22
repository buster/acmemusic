package de.acme.musicplayer.applications.users.usecases;

import de.acme.musicplayer.common.Event;

public interface UserEventDispatcher {
    void handleEvent(Event event);
}
