package de.acme.musicplayer.applications.scoreboard.usecases;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.applications.musicplayer.domain.events.NeuesLiedWurdeAngelegt;

@ModuleApi
public interface ZähleNeueLiederUsecase {
    void zähleNeueAngelegteLieder(NeuesLiedWurdeAngelegt event);
}
