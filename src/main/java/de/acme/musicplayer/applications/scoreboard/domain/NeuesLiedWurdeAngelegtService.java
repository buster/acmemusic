package de.acme.musicplayer.applications.scoreboard.domain;

import de.acme.musicplayer.applications.scoreboard.ports.UserScoreBoardRepository;
import de.acme.musicplayer.applications.scoreboard.usecases.NeuesLiedWurdeAngelegtUsecase;
import de.acme.musicplayer.events.NeuesLiedWurdeAngelegt;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NeuesLiedWurdeAngelegtService implements NeuesLiedWurdeAngelegtUsecase {

    private final UserScoreBoardRepository userScoreBoardRepository;

    public NeuesLiedWurdeAngelegtService(UserScoreBoardRepository userScoreBoardRepository) {
        this.userScoreBoardRepository = userScoreBoardRepository;
    }

    @Override
    public void neuesLiedWurdeAngelegt(NeuesLiedWurdeAngelegt event) {
        log.info("Received event: {}", event);
        userScoreBoardRepository.zähleNeuesLied(event.besitzerId());
    }
}
