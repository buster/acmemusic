package de.acme.musicplayer.applications.users.usecases;

import de.acme.musicplayer.applications.scoreboard.domain.events.BenutzerIstNeuerTopScorer;

public interface AuszeichnungFürNeueTopScorer {
    void vergebeAuszeichnungFürNeuenTopScorer(BenutzerIstNeuerTopScorer event);
}
