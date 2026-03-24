package de.acme.musicplayer.components.scoreboard.usecases;

import de.acme.musicplayer.components.musicplayer.domain.events.NeuesLiedWurdeAngelegt;
import de.acme.support.ModuleApi;

@ModuleApi
public interface ZaehleNeueLiederUsecase {
    void zähleNeueAngelegteLieder(NeuesLiedWurdeAngelegt event);
}
