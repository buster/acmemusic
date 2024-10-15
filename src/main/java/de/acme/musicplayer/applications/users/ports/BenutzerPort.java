package de.acme.musicplayer.applications.users.ports;

import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;

public interface BenutzerPort {
    Benutzer.Id benutzerHinzufügen(Benutzer benutzer, TenantId tenantId);

    long zaehleBenutzer(TenantId tenantId);

    void loescheDatenbank(TenantId tenantId);
}
