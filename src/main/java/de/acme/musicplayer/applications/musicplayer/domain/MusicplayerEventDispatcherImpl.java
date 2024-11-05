package de.acme.musicplayer.applications.musicplayer.domain;

import de.acme.musicplayer.applications.musicplayer.usecases.MusicplayerEventDispatcher;
import de.acme.musicplayer.common.Event;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MusicplayerEventDispatcherImpl implements MusicplayerEventDispatcher {

    @Override
    public void handleEvent(Event event) {
        log.info("Received event: " + event);
    }
}
