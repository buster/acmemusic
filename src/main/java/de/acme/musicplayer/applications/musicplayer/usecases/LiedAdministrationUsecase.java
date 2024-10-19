package de.acme.musicplayer.applications.musicplayer.usecases;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
@ModuleApi
public interface LiedAdministrationUsecase {

    long zähleLieder(TenantId tenantId);

    void löscheDatenbank(TenantId tenantId);

    Lied leseLied(Lied.Id id, TenantId tenantId);
}