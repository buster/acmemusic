package de.acme.musicplayer.components.scoreboard.usecases;

import de.acme.musicplayer.common.api.TenantId;

public interface ScoreBoardAdministrationUsecase {
    void löscheScoreboardDatenbank(TenantId tenantId);

    void löscheScoreboardEvents(TenantId tenantId);
}
