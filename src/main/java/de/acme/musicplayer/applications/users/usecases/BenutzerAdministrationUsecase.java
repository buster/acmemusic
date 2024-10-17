package de.acme.musicplayer.applications.users.usecases;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
@ModuleApi
public interface BenutzerAdministrationUsecase {

    long zähleBenutzer(TenantId tenantId);

    void löscheDatenbank(TenantId tenantId);
}
