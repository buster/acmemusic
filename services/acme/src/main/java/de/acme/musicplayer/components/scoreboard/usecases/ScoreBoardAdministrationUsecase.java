package de.acme.musicplayer.components.scoreboard.usecases;

import de.acme.musicplayer.common.TenantId;

public interface ScoreBoardAdministrationUsecase {
    void löscheDatenbank(TenantId tenantId);

    void löscheEvents(TenantId tenantId);
}
