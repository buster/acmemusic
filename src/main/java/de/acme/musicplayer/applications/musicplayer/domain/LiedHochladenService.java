package de.acme.musicplayer.applications.musicplayer.domain;

import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.events.NeuesLiedWurdeAngelegt;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import de.acme.musicplayer.applications.musicplayer.ports.EventPublisher;
import de.acme.musicplayer.applications.musicplayer.ports.LiedPort;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedHochladenUsecase;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;

public class LiedHochladenService implements LiedHochladenUsecase {

    private final LiedPort liedPort;
    private final EventPublisher eventPublisher;

    public LiedHochladenService(LiedPort liedPort, EventPublisher eventPublisher) {
        this.liedPort = liedPort;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public Lied.Id liedHochladen(Benutzer.Id benutzerId, Lied.Titel title, InputStream lied, TenantId tenantId) throws IOException {
        Lied neuesLied = Lied.neuesLied(title, benutzerId, tenantId);
        Lied.Id id = liedPort.fügeLiedHinzu(neuesLied, lied);
        eventPublisher.publishEvent(new NeuesLiedWurdeAngelegt(neuesLied.getId(),
                neuesLied.getTitel(),
                neuesLied.getBesitzer(),
                neuesLied.getTenantId())
        );
        return id;
    }
}
