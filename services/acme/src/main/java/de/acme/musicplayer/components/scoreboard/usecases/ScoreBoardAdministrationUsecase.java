package de.acme.musicplayer.components.scoreboard.usecases;

import de.acme.musicplayer.common.api.TenantId;
import de.acme.support.ModuleApi;

@ModuleApi
public interface ScoreBoardAdministrationUsecase {
    void löscheScoreboardDatenbank(TenantId tenantId);

    void löscheScoreboardEvents(TenantId tenantId);
}
