package de.acme.musicplayer.components.users.domain.events;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.TenantId;
import de.acme.musicplayer.common.events.Event;
import de.acme.musicplayer.components.users.domain.model.Auszeichnung;

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
