package de.acme.musicplayer.events;

import de.acme.musicplayer.ModuleApi;

@ModuleApi
public record NeuesLiedWurdeAngelegt(String id, String titel, String besitzerId,
                                     String tenantId) implements Event {

    @Override
    public String getTenant() {
        return tenantId;
    }
}
