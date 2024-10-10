package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.Lied;

public interface LiedHochladenUseCase {

    Lied.Id liedHochladen(String title);
}
