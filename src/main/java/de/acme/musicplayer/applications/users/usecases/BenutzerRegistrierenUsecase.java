package de.acme.musicplayer.applications.users.usecases;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.TenantId;

@ModuleApi
public interface BenutzerRegistrierenUsecase {

    BenutzerId registriereBenutzer(BenutzerRegistrierenCommand benutzerRegistrierenCommand);

    record BenutzerRegistrierenCommand(Benutzer.Name name, Benutzer.Passwort passwort, Benutzer.Email email, TenantId tenant) {
    }
}
