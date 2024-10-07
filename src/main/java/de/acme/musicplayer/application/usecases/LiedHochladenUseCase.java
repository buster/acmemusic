package de.acme.musicplayer.application.usecases;

import java.net.URI;

public interface LiedHochladenUseCase {

    void liedHochladen(String title, String artist, String album, String genre, String releaseYear, URI uri);
}
