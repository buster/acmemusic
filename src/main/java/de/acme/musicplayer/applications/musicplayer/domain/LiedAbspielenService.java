package de.acme.musicplayer.applications.musicplayer.domain;

import de.acme.musicplayer.applications.musicplayer.ports.LiedPort;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedAbspielenUsecase;
import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.TenantId;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

public class LiedAbspielenService implements LiedAbspielenUsecase {

    private final LiedPort liedPort;

    public LiedAbspielenService(LiedPort liedPort) {
        this.liedPort = liedPort;
    }

    @Override
    @Transactional
    public InputStream liedStreamen(LiedId liedId, TenantId tenantId) {
        return liedPort.ladeLiedStream(liedId, tenantId);
    }
}
