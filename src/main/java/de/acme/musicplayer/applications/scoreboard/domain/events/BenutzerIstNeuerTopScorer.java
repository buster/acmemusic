package de.acme.musicplayer.applications.scoreboard.domain.events;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.Event;
import de.acme.musicplayer.common.TenantId;
import jakarta.annotation.Nullable;

@ModuleApi
public record BenutzerIstNeuerTopScorer(BenutzerId neuerTopScorer, @Nullable BenutzerId alterTopScorer,
                                        TenantId tenantId) implements Event {
    @Override
    public TenantId getTenant() {
        return tenantId;
    }
}
