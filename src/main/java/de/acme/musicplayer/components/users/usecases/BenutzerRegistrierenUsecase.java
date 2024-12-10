package de.acme.musicplayer.components.users.usecases;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.TenantId;
import de.acme.musicplayer.components.users.domain.model.Benutzer;

@ModuleApi
public interface BenutzerRegistrierenUsecase {

    BenutzerId registriereBenutzer(BenutzerRegistrierenCommand benutzerRegistrierenCommand);

    record BenutzerRegistrierenCommand(Benutzer.Name name, Benutzer.Passwort passwort, Benutzer.Email email, TenantId tenant) {
    }
}
