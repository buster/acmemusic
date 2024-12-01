package de.acme.musicplayer.applications.musicplayer.domain;

import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.ports.LiedPort;
import de.acme.musicplayer.applications.musicplayer.ports.MusicplayerEventPublisher;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedAdministrationUsecase;
import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.TenantId;

public class LiedAdministrationService implements LiedAdministrationUsecase {

    private final LiedPort liedPort;
    private final MusicplayerEventPublisher musicPlayerEventPublisher;

    public LiedAdministrationService(LiedPort liedPort, MusicplayerEventPublisher musicPlayerEventPublisher) {
        this.liedPort = liedPort;
        this.musicPlayerEventPublisher = musicPlayerEventPublisher;
    }

    @Override
    public long zähleLieder(TenantId tenantId) {
        return liedPort.zähleLieder(tenantId);
    }

    @Override
    public void löscheDatenbank(TenantId tenantId) {
        liedPort.löscheDatenbank(tenantId);
    }

    @Override
    public Lied leseLied(LiedId liedId, TenantId tenantId) {
        return liedPort.leseLied(liedId, tenantId);
    }

    @Override
    public void löscheEvents(TenantId tenantId) {
        musicPlayerEventPublisher.removeEventsByTenantId(tenantId);
    }
}
