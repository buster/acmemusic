package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.TenantId;

public interface PlaylistAdministrationUsecase {

    void löscheDatenbank(TenantId tenantId);
}
