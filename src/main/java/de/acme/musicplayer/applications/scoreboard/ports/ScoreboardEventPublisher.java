package de.acme.musicplayer.applications.scoreboard.ports;

import de.acme.musicplayer.events.Event;

public interface ScoreboardEventPublisher {
    void publishEvent(Event event);

}
