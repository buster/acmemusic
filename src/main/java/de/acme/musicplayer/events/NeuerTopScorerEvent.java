package de.acme.musicplayer.events;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import jakarta.annotation.Nullable;

@ModuleApi
public record NeuerTopScorerEvent(Benutzer.Id neuerTopScorer, @Nullable Benutzer.Id alterTopScorer, TenantId tenantId) implements Event {
    @Override
    public TenantId getTenant() {
        return tenantId;
    }
}
