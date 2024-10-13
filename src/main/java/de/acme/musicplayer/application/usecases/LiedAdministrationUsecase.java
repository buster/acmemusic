package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.Lied;

public interface LiedAdministrationUsecase {

    long zähleLieder();

    void löscheDatenbank();

    void löscheLied(Lied.Id id);
}
