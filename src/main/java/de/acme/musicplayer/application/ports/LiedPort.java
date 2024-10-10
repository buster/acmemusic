package de.acme.musicplayer.application.ports;

import de.acme.musicplayer.application.domain.model.Lied;

public interface LiedPort {

    Lied ladeLied(Lied.Id songId);

    long zähleLieder();

    Lied.Id fügeLiedHinzu(Lied lied);

    void löscheDatenbank();
}
