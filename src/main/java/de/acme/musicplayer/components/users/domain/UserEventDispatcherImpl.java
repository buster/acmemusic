package de.acme.musicplayer.components.users.domain;

import de.acme.musicplayer.common.events.Event;
import de.acme.musicplayer.components.scoreboard.domain.events.BenutzerIstNeuerTopScorer;
import de.acme.musicplayer.components.users.usecases.BenutzerWurdeNeuerTopScorer;
import de.acme.musicplayer.components.users.usecases.UserEventDispatcher;

public class UserEventDispatcherImpl implements UserEventDispatcher {
    private final BenutzerWurdeNeuerTopScorer benutzerWurdeNeuerTopScorer;

    public UserEventDispatcherImpl(BenutzerWurdeNeuerTopScorer benutzerWurdeNeuerTopScorer) {
        this.benutzerWurdeNeuerTopScorer = benutzerWurdeNeuerTopScorer;
    }

    @Override
    public void handleEvent(Event event) {
        if (event instanceof BenutzerIstNeuerTopScorer benutzerIstNeuerTopScorer) {
            benutzerWurdeNeuerTopScorer.vergebeAuszeichnungFürNeuenTopScorer(benutzerIstNeuerTopScorer);
        }
    }
}
