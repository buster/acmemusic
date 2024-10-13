package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.ports.LiedPort;
import de.acme.musicplayer.application.usecases.LiedAdministrationUsecase;
import de.acme.musicplayer.application.domain.model.TenantId;
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
    @Transactional
    public void löscheDatenbank(TenantId tenantId) {
        liedPort.löscheDatenbank(tenantId);
    }
}
