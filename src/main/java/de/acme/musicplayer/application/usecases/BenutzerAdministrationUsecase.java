package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.Benutzer;

public interface BenutzerAdministrationUsecase {

    long zähleBenutzer();

    void löscheDatenbank();

    void löscheBenutzer(Benutzer.Id id);
}
