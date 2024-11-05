package de.acme.musicplayer.components.users.usecases;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.TenantId;
import de.acme.musicplayer.components.users.domain.model.Benutzer;

@ModuleApi
public interface BenutzerAdministrationUsecase {

    long zähleBenutzer(TenantId tenantId);

    void löscheDatenbank(TenantId tenantId);

    Benutzer leseBenutzer(BenutzerId benutzerId, TenantId tenantId);

    void löscheEvents(TenantId tenantId);

}
