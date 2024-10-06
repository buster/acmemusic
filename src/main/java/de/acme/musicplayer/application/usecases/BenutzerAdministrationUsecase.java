package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.TenantId;

public interface BenutzerAdministrationUsecase {

    long zähleBenutzer(TenantId tenantId);

    void löscheDatenbank(TenantId tenantId);
}
