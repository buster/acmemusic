package de.acme.musicplayer.applications.musicplayer.usecases;

import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;

public interface PlaylistAdministrationUsecase {

    void löscheDatenbank(TenantId tenantId);
}
