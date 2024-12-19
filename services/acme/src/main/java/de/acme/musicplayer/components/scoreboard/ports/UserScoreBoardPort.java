package de.acme.musicplayer.components.scoreboard.ports;

import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.TenantId;

public interface UserScoreBoardPort {
    void zähleNeuesLied(BenutzerId benutzerId, TenantId tenant);

    BenutzerId höchstePunktZahl(TenantId tenantId);

    void löscheDatenbank(TenantId tenantId);
}
