package de.acme.musicplayer.applications.musicplayer.domain;

import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.ports.LiedPort;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedAdministrationUsecase;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import org.springframework.transaction.annotation.Transactional;

public class LiedAdministrationService implements LiedAdministrationUsecase {

    private final LiedPort liedPort;

    public LiedAdministrationService(LiedPort liedPort) {
        this.liedPort = liedPort;
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
    public Lied leseLied(Lied.Id id, TenantId tenantId) {
        return liedPort.leseLied(id, tenantId);
    }
}
