package de.acme.musicplayer.events;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import de.acme.musicplayer.applications.users.domain.model.Auszeichnung;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;

@ModuleApi
public record BenutzerHatNeueAuszeichnungEvent(Benutzer.Id id, Auszeichnung auszeichnung,
                                               TenantId tenantId) implements Event {
    @Override
    public TenantId getTenant() {
        return tenantId;
    }
}
