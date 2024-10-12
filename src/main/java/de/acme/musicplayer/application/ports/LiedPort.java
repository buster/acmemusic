package de.acme.musicplayer.application.ports;

import de.acme.musicplayer.application.domain.model.Lied;

import java.io.IOException;
import java.io.InputStream;

public interface LiedPort {

    Lied ladeLied(Lied.Id liedId);

    long zähleLieder();

    Lied.Id fügeLiedHinzu(Lied lied, InputStream inputStream) throws IOException;

    void löscheDatenbank();

    InputStream ladeLiedStream(Lied.Id liedId);
}
