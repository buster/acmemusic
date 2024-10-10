package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.Lied;

public interface LiedAbspielenUsecase {
    void spieleLiedAb(Lied.Id liedId);
}
