package de.acme.musicplayer.components.musicplayer.adapters.events;

import de.acme.musicplayer.common.events.Event;
import de.acme.musicplayer.common.events.EventDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MusicPlayerEventListeners implements EventDispatcher {


    @Override
    @EventListener
    @Async
    public void handleEvent(Event event) {
        log.info("Received event: " + event);
    }

}
