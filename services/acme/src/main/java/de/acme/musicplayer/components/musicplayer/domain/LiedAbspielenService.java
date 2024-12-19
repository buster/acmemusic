package de.acme.musicplayer.components.musicplayer.domain;

import de.acme.musicplayer.common.api.LiedId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.musicplayer.ports.LiedPort;
import de.acme.musicplayer.components.musicplayer.usecases.LiedAbspielenUsecase;
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
