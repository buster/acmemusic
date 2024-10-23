package de.acme.musicplayer.applications.users.ports;

import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.TenantId;

public interface BenutzerPort {
    BenutzerId benutzerHinzufügen(Benutzer benutzer, TenantId tenantId);

    long zaehleBenutzer(TenantId tenantId);

    void loescheDatenbank(TenantId tenantId);

    Benutzer leseBenutzer(BenutzerId benutzerId, TenantId tenantId);

    void speichereBenutzer(Benutzer neuerTopScorer, TenantId tenant);
}
