package de.acme.musicplayer.applications.users.usecases;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;

@ModuleApi
public interface BenutzerAdministrationUsecase {

    long zähleBenutzer(TenantId tenantId);

    void löscheDatenbank(TenantId tenantId);

    Benutzer leseBenutzer(Benutzer.Id id, TenantId tenantId);
}
