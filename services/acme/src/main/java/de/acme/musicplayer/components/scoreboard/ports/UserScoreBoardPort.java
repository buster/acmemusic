package de.acme.musicplayer.components.scoreboard.ports;

import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.TenantId;
import jakarta.annotation.Nullable;

public interface UserScoreBoardPort {
    void zähleNeuesLied(BenutzerId benutzerId, TenantId tenant);

    @Nullable
    BenutzerId findeBenutzerMitHöchsterPunktZahl(TenantId tenantId);

    void löscheDatenbank(TenantId tenantId);
}
