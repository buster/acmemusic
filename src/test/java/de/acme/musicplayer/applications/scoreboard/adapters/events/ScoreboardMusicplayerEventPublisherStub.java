package de.acme.musicplayer.applications.scoreboard.adapters.events;

import de.acme.musicplayer.applications.scoreboard.domain.events.BenutzerIstNeuerTopScorer;
import de.acme.musicplayer.applications.scoreboard.ports.ScoreboardEventPublisher;
import de.acme.musicplayer.applications.users.usecases.AuszeichnungFürNeueTopScorer;
import de.acme.musicplayer.common.Event;

public class ScoreboardMusicplayerEventPublisherStub implements ScoreboardEventPublisher {

    private final AuszeichnungFürNeueTopScorer neuerTopScorerGefunden;

    public ScoreboardMusicplayerEventPublisherStub(AuszeichnungFürNeueTopScorer neuerTopScorerGefunden) {
        this.neuerTopScorerGefunden = neuerTopScorerGefunden;
    }


    @Override
    public void publishEvent(Event event) {
        if (event instanceof BenutzerIstNeuerTopScorer) {
            neuerTopScorerGefunden.vergebeAuszeichnungFürNeuenTopScorer((BenutzerIstNeuerTopScorer) event);
        } else {
            throw new IllegalArgumentException("Unknown event type: " + event.getClass().getName());
        }
    }
}
