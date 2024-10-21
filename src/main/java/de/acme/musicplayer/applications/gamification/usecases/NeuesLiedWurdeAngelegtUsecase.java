package de.acme.musicplayer.applications.gamification.usecases;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.events.NeuesLiedWurdeAngelegt;

@ModuleApi
public interface NeuesLiedWurdeAngelegtUsecase {
    void neuesLiedWurdeAngelegt(NeuesLiedWurdeAngelegt event);
}
