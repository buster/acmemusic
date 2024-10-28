package de.acme.musicplayer.applications.users.domain;

import de.acme.musicplayer.applications.scoreboard.domain.events.BenutzerIstNeuerTopScorer;
import de.acme.musicplayer.applications.users.usecases.AuszeichnungFürNeueTopScorer;
import de.acme.musicplayer.applications.users.usecases.UserEventDispatcher;
import de.acme.musicplayer.common.Event;

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
