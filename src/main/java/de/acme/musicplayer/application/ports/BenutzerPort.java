package de.acme.musicplayer.application.ports;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.TenantId;

public interface BenutzerPort {
    Benutzer.Id benutzerHinzufügen(Benutzer benutzer, TenantId tenantId);

    long zaehleBenutzer(TenantId tenantId);

    void loescheDatenbank(TenantId tenantId);
}
