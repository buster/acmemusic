package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.TenantId;

public interface LiedAdministrationUsecase {

    long zähleLieder(TenantId tenantId);

    void löscheDatenbank(TenantId tenantId);
}
