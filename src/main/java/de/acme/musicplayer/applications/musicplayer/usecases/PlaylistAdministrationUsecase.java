package de.acme.musicplayer.applications.musicplayer.usecases;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
@ModuleApi
public interface PlaylistAdministrationUsecase {

    void löscheDatenbank(TenantId tenantId);
}
