package de.acme.musicplayer.applications.scoreboard.usecases;

import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;

public interface ScoreBoardAdministrationUsecase {
    void löscheDatenbank(TenantId tenantId);
}
