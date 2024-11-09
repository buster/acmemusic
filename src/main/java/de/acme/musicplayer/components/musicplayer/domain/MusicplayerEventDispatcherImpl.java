package de.acme.musicplayer.components.musicplayer.domain;

import de.acme.musicplayer.common.events.Event;
import de.acme.musicplayer.components.musicplayer.usecases.MusicplayerEventDispatcher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MusicplayerEventDispatcherImpl implements MusicplayerEventDispatcher {

    @Override
    public void handleEvent(Event event) {
        log.info("Received event: " + event);
    }
}
