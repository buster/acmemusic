package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.Benutzer;

public interface BenutzerRegistrierenUsecase {

    Benutzer.Id benutzerAnmelden(BenutzerAnmeldenCommand benutzerAnmeldenCommand);

    record BenutzerAnmeldenCommand(Benutzer.Name name, Benutzer.Passwort passwort, Benutzer.Email email) {
    }
}
