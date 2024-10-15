package de.acme.musicplayer.applications.users.domain;

import de.acme.musicplayer.applications.musicplayer.adapters.jdbc.LiedRepository;
import de.acme.musicplayer.applications.musicplayer.ports.LiedPort;
import de.acme.musicplayer.applications.users.ports.BenutzerPort;
import de.acme.musicplayer.applications.users.usecases.BenutzerAdministrationUsecase;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import org.springframework.transaction.annotation.Transactional;

public class BenutzerAdministrationService implements BenutzerAdministrationUsecase {

    private final BenutzerPort benutzerPort;
    public LiedPort liedPort = new LiedRepository(null);

    public BenutzerAdministrationService(BenutzerPort benutzerPort) {
        this.benutzerPort = benutzerPort;
//        this.liedPort = liedPort;
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
}
