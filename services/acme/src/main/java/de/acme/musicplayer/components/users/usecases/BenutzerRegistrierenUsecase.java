package de.acme.musicplayer.components.users.usecases;

import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.users.domain.model.Benutzer;
import de.acme.support.ModuleApi;

@ModuleApi
public interface BenutzerRegistrierenUsecase {

    BenutzerId registriereBenutzer(BenutzerRegistrierenCommand benutzerRegistrierenCommand);

    record BenutzerRegistrierenCommand(Benutzer.Name name, Benutzer.Passwort passwort, Benutzer.Email email,
                                       TenantId tenant) {
    }
}
