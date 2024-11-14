package de.acme.musicplayer.components.scoreboard.domain;

import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.common.events.EventPublisher;
import de.acme.musicplayer.components.scoreboard.ports.UserScoreBoardPort;
import de.acme.musicplayer.components.scoreboard.usecases.ScoreBoardAdministrationUsecase;

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
        eventPublisher.removeAllEventsFromOutboxByTenantId(tenantId);
    }
}
