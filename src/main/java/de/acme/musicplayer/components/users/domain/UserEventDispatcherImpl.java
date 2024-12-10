package de.acme.musicplayer.components.users.domain;

import de.acme.musicplayer.common.events.Event;
import de.acme.musicplayer.components.scoreboard.domain.events.BenutzerIstNeuerTopScorer;
import de.acme.musicplayer.components.users.usecases.AuszeichnungFürNeueTopScorer;
import de.acme.musicplayer.components.users.usecases.UserEventDispatcher;

public class UserEventDispatcherImpl implements UserEventDispatcher {
    private final AuszeichnungFürNeueTopScorer auszeichnungFürNeueTopScorer;

    public UserEventDispatcherImpl(AuszeichnungFürNeueTopScorer auszeichnungFürNeueTopScorer) {
        this.auszeichnungFürNeueTopScorer = auszeichnungFürNeueTopScorer;
    }

    @Override
    public void handleEvent(Event event) {
        if (event instanceof BenutzerIstNeuerTopScorer benutzerIstNeuerTopScorer) {
            auszeichnungFürNeueTopScorer.vergebeAuszeichnungFürNeuenTopScorer(benutzerIstNeuerTopScorer);
        }
    }
}
