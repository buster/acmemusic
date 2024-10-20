package de.acme.musicplayer.applications.scoreboard.usecases;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.events.NeuesLiedWurdeAngelegt;

@ModuleApi
public interface NeuesLiedWurdeAngelegtUsecase {
    void neuesLiedWurdeAngelegt(NeuesLiedWurdeAngelegt event);
}
