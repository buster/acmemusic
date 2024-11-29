package de.acme.musicplayer.applications.scoreboard.domain;

import de.acme.musicplayer.applications.scoreboard.ports.UserScoreBoardPort;
import de.acme.musicplayer.applications.scoreboard.usecases.ScoreBoardAdministrationUsecase;
import de.acme.musicplayer.common.EventPublisher;
import de.acme.musicplayer.common.TenantId;

public class ScoreBoardAdministrationService implements ScoreBoardAdministrationUsecase {

    private final UserScoreBoardPort userScoreBoardPort;
    private final EventPublisher eventPublisher;

    public ScoreBoardAdministrationService(UserScoreBoardPort userScoreBoardPort, EventPublisher eventPublisher) {
        this.userScoreBoardPort = userScoreBoardPort;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void löscheDatenbank(TenantId tenantId) {
        userScoreBoardPort.löscheDatenbank(tenantId);
    }

    @Override
    public void löscheEvents(TenantId tenantId) {
        eventPublisher.removeEventsByTenantId(tenantId);
    }
}
