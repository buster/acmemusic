package de.acme.musicplayer.application.ports;

import de.acme.musicplayer.application.domain.model.Lied;

import java.net.URI;

public interface LiedHinzufügenPort {

    void fügeLiedHinzu(Lied lied);
}
