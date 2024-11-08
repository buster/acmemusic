package de.acme.musicplayer.applications.scoreboard.adapters.events;

import de.acme.musicplayer.applications.scoreboard.ports.ScoreboardEventPublisher;
import de.acme.musicplayer.applications.users.usecases.BenutzerIstTopScorerUsecase;
import de.acme.musicplayer.events.Event;
import de.acme.musicplayer.events.NeuerTopScorerEvent;

public class ScoreboardMusicplayerEventPublisherStub implements ScoreboardEventPublisher {

    private final BenutzerIstTopScorerUsecase neuerTopScorerGefunden;

    public ScoreboardMusicplayerEventPublisherStub(BenutzerIstTopScorerUsecase neuerTopScorerGefunden) {
        this.neuerTopScorerGefunden = neuerTopScorerGefunden;
    }


    @Override
    public void publishEvent(Event event) {
        if (event instanceof NeuerTopScorerEvent) {
            neuerTopScorerGefunden.neuerTopScorerGefunden((NeuerTopScorerEvent) event);
        } else {
            throw new IllegalArgumentException("Unknown event type: " + event.getClass().getName());
        }
    }
}
