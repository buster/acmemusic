package de.acme.musicplayer.applications.users.usecases;

import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;

public interface BenutzerRegistrierenUsecase {

    Benutzer.Id registriereBenutzer(BenutzerRegistrierenCommand benutzerRegistrierenCommand);

    record BenutzerRegistrierenCommand(Benutzer.Name name, Benutzer.Passwort passwort, Benutzer.Email email, TenantId tenant) {
    }
}
