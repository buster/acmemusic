package de.acme.musicplayer.applications.users.usecases;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.TenantId;

@ModuleApi
public interface BenutzerAdministrationUsecase {

    long zähleBenutzer(TenantId tenantId);

    void löscheDatenbank(TenantId tenantId);

    Benutzer leseBenutzer(BenutzerId benutzerId, TenantId tenantId);

    void löscheEvents(TenantId tenantId);

}
