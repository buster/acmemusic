package de.acme.musicplayer.applications.scoreboard.ports;

import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;

public interface UserScoreBoardPort {
    void zähleNeuesLied(Benutzer.Id benutzerId, TenantId tenant);

    Benutzer.Id höchstePunktZahl(TenantId tenantId);

    void löscheDatenbank(TenantId tenantId);
}