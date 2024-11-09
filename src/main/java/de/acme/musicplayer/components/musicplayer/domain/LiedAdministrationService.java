package de.acme.musicplayer.components.musicplayer.domain;

import de.acme.musicplayer.common.TenantId;
import de.acme.musicplayer.components.musicplayer.ports.LiedPort;
import de.acme.musicplayer.components.musicplayer.ports.MusicplayerEventPublisher;
import de.acme.musicplayer.components.musicplayer.usecases.LiedAdministrationUsecase;

public class LiedAdministrationService implements LiedAdministrationUsecase {

    private final LiedPort liedPort;
    private final MusicplayerEventPublisher musicPlayerEventPublisher;

    public LiedAdministrationService(LiedPort liedPort, MusicplayerEventPublisher musicPlayerEventPublisher) {
        this.liedPort = liedPort;
        this.musicPlayerEventPublisher = musicPlayerEventPublisher;
    }

    @Override
    public void löscheDatenbank(TenantId tenantId) {
        liedPort.löscheDatenbank(tenantId);
    }

    @Override
    public void löscheEvents(TenantId tenantId) {
        musicPlayerEventPublisher.removeEventsByTenantId(tenantId);
    }
}
