package de.acme.musicplayer.components.scoreboard.domain.events;

import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.common.events.Event;
import de.acme.support.ModuleApi;
import jakarta.annotation.Nullable;

@ModuleApi
public record BenutzerIstNeuerTopScorer(BenutzerId neuerTopScorer, @Nullable BenutzerId alterTopScorer,
                                        TenantId tenantId) implements Event {
    @Override
    public TenantId getTenant() {
        return tenantId;
    }
}
