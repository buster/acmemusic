package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.ports.BenutzerPort;
import de.acme.musicplayer.application.usecases.BenutzerAdministrationUsecase;
import de.acme.musicplayer.application.domain.model.TenantId;

public class BenutzerAdministrationService implements BenutzerAdministrationUsecase {

    private final BenutzerPort benutzerPort;

    public BenutzerAdministrationService(BenutzerPort benutzerPort) {
        this.benutzerPort = benutzerPort;
    }

    @Override
    public long zähleBenutzer(TenantId tenantId) {
        return benutzerPort.zaehleBenutzer(tenantId);
    }

    @Override
    public void löscheDatenbank(TenantId tenantId) {
        benutzerPort.loescheDatenbank(tenantId);
    }
}
