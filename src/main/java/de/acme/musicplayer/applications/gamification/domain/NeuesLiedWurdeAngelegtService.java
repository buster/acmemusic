package de.acme.musicplayer.applications.gamification.domain;

import de.acme.musicplayer.applications.gamification.usecases.NeuesLiedWurdeAngelegtUsecase;
import de.acme.musicplayer.events.NeuesLiedWurdeAngelegt;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NeuesLiedWurdeAngelegtService implements NeuesLiedWurdeAngelegtUsecase {

    @Override
    public void neuesLiedWurdeAngelegt(NeuesLiedWurdeAngelegt event) {
        log.info("Received event: {}", event);
    }
}
