package de.acme.musicplayer.components.users.ports;

import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.users.domain.model.Benutzer;

public interface BenutzerPort {
    BenutzerId benutzerHinzufügen(Benutzer benutzer, TenantId tenantId);

    long zaehleBenutzer(TenantId tenantId);

    void loescheDatenbank(TenantId tenantId);

    Benutzer leseBenutzer(BenutzerId benutzerId, TenantId tenantId);

    void speichereBenutzer(Benutzer neuerTopScorer, TenantId tenant);
}
