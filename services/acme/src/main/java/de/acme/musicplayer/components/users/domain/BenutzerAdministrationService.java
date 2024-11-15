package de.acme.musicplayer.components.users.domain;

import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.common.events.EventPublisher;
import de.acme.musicplayer.components.users.domain.model.Benutzer;
import de.acme.musicplayer.components.users.ports.BenutzerPort;
import de.acme.musicplayer.components.users.usecases.BenutzerAdministrationUsecase;
import org.springframework.transaction.annotation.Transactional;

public class BenutzerAdministrationService implements BenutzerAdministrationUsecase {

    private final BenutzerPort benutzerPort;
    private final EventPublisher userEventPublisher;

    public BenutzerAdministrationService(BenutzerPort benutzerPort, EventPublisher userEventPublisher) {
        this.benutzerPort = benutzerPort;
        this.userEventPublisher = userEventPublisher;
    }

    @Override
    public long zähleBenutzer(TenantId tenantId) {
        return benutzerPort.zaehleBenutzer(tenantId);
    }

    @Override
    @Transactional
    public void löscheBenutzerDatenbank(TenantId tenantId) {
        benutzerPort.loescheDatenbank(tenantId);
    }

    @Override
    public Benutzer leseBenutzer(BenutzerId benutzerId, TenantId tenantId) {
        return benutzerPort.leseBenutzer(benutzerId, tenantId);
    }

    @Override
    public void löscheBenutzerEvents(TenantId tenantId) {
        userEventPublisher.removeAllEventsFromOutboxByTenantId(tenantId);
    }
}
