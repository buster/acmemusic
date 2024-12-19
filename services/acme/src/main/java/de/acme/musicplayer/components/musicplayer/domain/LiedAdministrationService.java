package de.acme.musicplayer.components.musicplayer.domain;

import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.common.events.EventPublisher;
import de.acme.musicplayer.components.musicplayer.ports.LiedPort;
import de.acme.musicplayer.components.musicplayer.usecases.LiedAdministrationUsecase;

public class LiedAdministrationService implements LiedAdministrationUsecase {

    private final LiedPort liedPort;
    private final EventPublisher musicPlayerEventPublisher;

    public LiedAdministrationService(LiedPort liedPort, EventPublisher musicPlayerEventPublisher) {
        this.liedPort = liedPort;
        this.musicPlayerEventPublisher = musicPlayerEventPublisher;
    }

    @Override
    public void löscheDatenbank(TenantId tenantId) {
        liedPort.löscheDatenbank(tenantId);
    }

    @Override
    public void löscheEvents(TenantId tenantId) {
        musicPlayerEventPublisher.removeAllEventsFromOutboxByTenantId(tenantId);
    }
}
