package de.acme.musicplayer.components.scoreboard.usecases;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.components.musicplayer.domain.events.NeuesLiedWurdeAngelegt;

@ModuleApi
public interface ZähleNeueLiederUsecase {
    void zähleNeueAngelegteLieder(NeuesLiedWurdeAngelegt event);
}
