package de.acme.musicplayer.applications.scoreboard.domain;

import de.acme.musicplayer.applications.scoreboard.ports.UserScoreBoardPort;
import de.acme.musicplayer.applications.scoreboard.usecases.ScoreBoardAdministrationUsecase;
import de.acme.musicplayer.common.TenantId;

public class ScoreBoardAdministrationService implements ScoreBoardAdministrationUsecase {

    private final UserScoreBoardPort userScoreBoardPort;

    public ScoreBoardAdministrationService(UserScoreBoardPort userScoreBoardPort) {
        this.userScoreBoardPort = userScoreBoardPort;
    }

    @Override
    public void löscheDatenbank(TenantId tenantId) {
        userScoreBoardPort.löscheDatenbank(tenantId);
    }
}
