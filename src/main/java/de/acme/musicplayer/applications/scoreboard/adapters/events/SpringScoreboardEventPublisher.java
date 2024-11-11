package de.acme.musicplayer.applications.scoreboard.adapters.events;

import de.acme.musicplayer.applications.scoreboard.ports.ScoreboardEventPublisher;
import de.acme.musicplayer.events.Event;
import de.acme.musicplayer.applications.musicplayer.ports.MusicplayerEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringScoreboardEventPublisher implements ScoreboardEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public SpringScoreboardEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public void publishEvent(Event event) {
        applicationEventPublisher.publishEvent(event);
    }
}
