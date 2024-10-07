package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.Lied;

import java.net.URI;

public interface LiedHochladenUseCase {

    Lied.LiedId liedHochladen(String title, String artist, String album, String genre, String releaseYear, URI uri);
}
