package de.acme.musicplayer.components.musicplayer.domain;

import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.TenantId;
import de.acme.musicplayer.common.events.EventPublisher;
import de.acme.musicplayer.components.musicplayer.domain.events.NeuesLiedWurdeAngelegt;
import de.acme.musicplayer.components.musicplayer.domain.model.Lied;
import de.acme.musicplayer.components.musicplayer.ports.LiedPort;
import de.acme.musicplayer.components.musicplayer.usecases.LiedHochladenUsecase;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;

public class LiedHochladenService implements LiedHochladenUsecase {

    private final LiedPort liedPort;
    private final EventPublisher musicplayerEventPublisher;

    public LiedHochladenService(LiedPort liedPort, EventPublisher musicplayerEventPublisher) {
        this.liedPort = liedPort;
        this.musicplayerEventPublisher = musicplayerEventPublisher;
    }

    @Override
    @Transactional
    public LiedId liedHochladen(BenutzerId benutzerId, Lied.Titel title, InputStream lied, TenantId tenantId) throws IOException {
        Lied neuesLied = Lied.neuesLied(title, benutzerId, tenantId);
        LiedId liedId = liedPort.fügeLiedHinzu(neuesLied, lied);
        NeuesLiedWurdeAngelegt event = new NeuesLiedWurdeAngelegt(neuesLied.getId(),
                neuesLied.getBesitzer(),
                tenantId);
        musicplayerEventPublisher.publishEvent(event, tenantId);
        return liedId;
    }
}
