package de.acme.musicplayer.applications.musicplayer.domain;

import de.acme.musicplayer.applications.musicplayer.ports.LiedPort;
import de.acme.musicplayer.applications.musicplayer.ports.MusicplayerEventPublisher;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedAdministrationUsecase;
import de.acme.musicplayer.common.TenantId;

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
