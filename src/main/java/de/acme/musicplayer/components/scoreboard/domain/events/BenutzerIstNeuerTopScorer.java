package de.acme.musicplayer.components.scoreboard.domain.events;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.TenantId;
import de.acme.musicplayer.common.events.Event;
import jakarta.annotation.Nullable;

@ModuleApi
public record BenutzerIstNeuerTopScorer(BenutzerId neuerTopScorer, @Nullable BenutzerId alterTopScorer,
                                        TenantId tenantId) implements Event {
    @Override
    public TenantId getTenant() {
        return tenantId;
    }
}
