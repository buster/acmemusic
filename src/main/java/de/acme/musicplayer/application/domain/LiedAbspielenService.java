package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.ports.LiedPort;
import de.acme.musicplayer.application.usecases.LiedAbspielenUsecase;
import de.acme.musicplayer.application.domain.model.TenantId;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

public class LiedAbspielenService implements LiedAbspielenUsecase {

    private final LiedPort liedPort;

    public LiedAbspielenService(LiedPort liedPort) {
        this.liedPort = liedPort;
    }

    @Override
    @Transactional
    public InputStream liedStreamen(Benutzer.Id id, Lied.Id liedId, TenantId tenantId) {
        return liedPort.ladeLiedStream(liedId, tenantId);
    }
}
