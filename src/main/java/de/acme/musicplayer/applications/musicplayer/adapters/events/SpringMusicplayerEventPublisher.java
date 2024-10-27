package de.acme.musicplayer.applications.musicplayer.adapters.events;

import de.acme.musicplayer.applications.musicplayer.ports.MusicplayerEventPublisher;
import de.acme.musicplayer.common.Event;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringMusicplayerEventPublisher implements MusicplayerEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public SpringMusicplayerEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public void publishEvent(Event event) {
        applicationEventPublisher.publishEvent(event);
    }
}
