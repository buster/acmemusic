package de.acme.musicplayer.applications.users.domain;

import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.applications.users.ports.BenutzerPort;
import de.acme.musicplayer.applications.users.ports.UserEventPublisher;
import de.acme.musicplayer.applications.users.usecases.BenutzerAdministrationUsecase;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.TenantId;
import org.springframework.transaction.annotation.Transactional;

public class BenutzerAdministrationService implements BenutzerAdministrationUsecase {

    private final BenutzerPort benutzerPort;
    private final UserEventPublisher userEventPublisher;

    public BenutzerAdministrationService(BenutzerPort benutzerPort, UserEventPublisher userEventPublisher) {
        this.benutzerPort = benutzerPort;
        this.userEventPublisher = userEventPublisher;
    }

    @Override
    public long zähleBenutzer(TenantId tenantId) {
        return benutzerPort.zaehleBenutzer(tenantId);
    }

    @Override
    @Transactional
    public void löscheDatenbank(TenantId tenantId) {
        benutzerPort.loescheDatenbank(tenantId);
    }

    @Override
    public Benutzer leseBenutzer(BenutzerId benutzerId, TenantId tenantId) {
        return benutzerPort.leseBenutzer(benutzerId, tenantId);
    }

    @Override
    public void löscheEvents(TenantId tenantId) {
        userEventPublisher.removeEventsByTenantId(tenantId);
    }
}
