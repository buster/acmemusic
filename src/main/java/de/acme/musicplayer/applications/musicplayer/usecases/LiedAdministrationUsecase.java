package de.acme.musicplayer.applications.musicplayer.usecases;

import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;

public interface LiedAdministrationUsecase {

    long zähleLieder(TenantId tenantId);

    void löscheDatenbank(TenantId tenantId);
}
