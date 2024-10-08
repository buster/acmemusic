package de.acme.musicplayer.application.usecases;

import java.net.URI;

public interface LiedHochladenUseCase {

    String liedHochladen(String title, String artist, String album, String genre, String releaseYear, URI uri);
}
