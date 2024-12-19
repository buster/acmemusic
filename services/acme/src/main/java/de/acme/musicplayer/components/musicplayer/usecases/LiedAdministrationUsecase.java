package de.acme.musicplayer.components.musicplayer.usecases;

import de.acme.musicplayer.common.api.TenantId;
import de.acme.support.ModuleApi;

@ModuleApi
public interface LiedAdministrationUsecase {

    void löscheDatenbank(TenantId tenantId);

    void löscheEvents(TenantId tenantId);

}
