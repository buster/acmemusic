package de.acme.musicplayer.components.users.domain.events;

import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.common.events.Event;
import de.acme.musicplayer.components.users.domain.model.Auszeichnung;
import de.acme.support.ModuleApi;

@ModuleApi
public record BenutzerHatNeueAuszeichnungErhalten(BenutzerId benutzerId,
                                                  String benutzername,
                                                  Auszeichnung auszeichnung,
                                                  TenantId tenantId) implements Event {
    @Override
    public TenantId getTenant() {
        return tenantId;
    }
}
