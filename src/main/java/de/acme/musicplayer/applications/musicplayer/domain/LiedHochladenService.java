package de.acme.musicplayer.applications.musicplayer.domain;

import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.ports.LiedPort;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedHochladenUsecase;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;

public class LiedHochladenService implements LiedHochladenUsecase {

    private final LiedPort liedPort;

    public LiedHochladenService(LiedPort liedPort) {
        this.liedPort = liedPort;
    }

    @Override
    @Transactional
    public Lied.Id liedHochladen(Lied.Titel title, InputStream lied, TenantId tenantId) throws IOException {
        return liedPort.fügeLiedHinzu(new Lied(title), lied, tenantId);
    }
}
