package de.acme.musicplayer.applications.users.domain.events;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.applications.users.domain.model.Auszeichnung;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.Event;
import de.acme.musicplayer.common.TenantId;

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
