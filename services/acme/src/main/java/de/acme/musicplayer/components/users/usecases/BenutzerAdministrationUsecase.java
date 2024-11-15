package de.acme.musicplayer.components.users.usecases;

import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.users.domain.model.Benutzer;
import de.acme.support.ModuleApi;

@ModuleApi
public interface BenutzerAdministrationUsecase {

    long zähleBenutzer(TenantId tenantId);

    void löscheBenutzerDatenbank(TenantId tenantId);

    Benutzer leseBenutzer(BenutzerId benutzerId, TenantId tenantId);

    void löscheBenutzerEvents(TenantId tenantId);

}
