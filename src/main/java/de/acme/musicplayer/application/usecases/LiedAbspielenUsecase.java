package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;

import java.io.InputStream;

public interface LiedAbspielenUsecase {
    InputStream liedStreamen(Benutzer.Id id, Lied.Id liedId);
}
