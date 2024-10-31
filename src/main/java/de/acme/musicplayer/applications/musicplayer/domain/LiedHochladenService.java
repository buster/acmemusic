package de.acme.musicplayer.applications.musicplayer.domain;

import de.acme.musicplayer.applications.musicplayer.domain.events.NeuesLiedWurdeAngelegt;
import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.ports.LiedPort;
import de.acme.musicplayer.applications.musicplayer.ports.MusicplayerEventPublisher;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedHochladenUsecase;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.TenantId;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;

public class LiedHochladenService implements LiedHochladenUsecase {

    private final LiedPort liedPort;
    private final MusicplayerEventPublisher musicplayerEventPublisher;

    public LiedHochladenService(LiedPort liedPort, MusicplayerEventPublisher musicplayerEventPublisher) {
        this.liedPort = liedPort;
        this.musicplayerEventPublisher = musicplayerEventPublisher;
    }

    @Override
    @Transactional
    public LiedId liedHochladen(BenutzerId benutzerId, Lied.Titel title, InputStream lied, TenantId tenantId) throws IOException {
        Lied neuesLied = Lied.neuesLied(title, benutzerId, tenantId);
        LiedId liedId = liedPort.fügeLiedHinzu(neuesLied, lied);
        musicplayerEventPublisher.publishEvent(new NeuesLiedWurdeAngelegt(neuesLied.getId(),
                neuesLied.getBesitzer(),
                neuesLied.getTenantId())
        );
        return liedId;
    }
}
