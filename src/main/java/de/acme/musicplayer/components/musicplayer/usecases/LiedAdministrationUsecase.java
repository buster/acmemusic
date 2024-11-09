package de.acme.musicplayer.components.musicplayer.usecases;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.common.TenantId;

@ModuleApi
public interface LiedAdministrationUsecase {

    void löscheDatenbank(TenantId tenantId);

    void löscheEvents(TenantId tenantId);

}
