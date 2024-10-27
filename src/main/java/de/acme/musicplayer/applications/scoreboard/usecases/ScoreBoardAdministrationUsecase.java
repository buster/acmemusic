package de.acme.musicplayer.applications.scoreboard.usecases;

import de.acme.musicplayer.common.TenantId;

public interface ScoreBoardAdministrationUsecase {
    void löscheDatenbank(TenantId tenantId);
}
