package de.acme.musicplayer.application.ports;

import de.acme.musicplayer.application.domain.model.Lied;

public interface LiedPort {

    Lied ladeLied(Lied.LiedId songId);

    long zähleLieder();

    Lied.LiedId fügeLiedHinzu(Lied lied);

    void löscheDatenbank();
}
