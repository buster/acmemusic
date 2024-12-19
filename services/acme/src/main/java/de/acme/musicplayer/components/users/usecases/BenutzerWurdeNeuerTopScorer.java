package de.acme.musicplayer.components.users.usecases;

import de.acme.musicplayer.components.scoreboard.domain.events.BenutzerIstNeuerTopScorer;

public interface BenutzerWurdeNeuerTopScorer {
    void vergebeAuszeichnungFürNeuenTopScorer(BenutzerIstNeuerTopScorer event);
}
