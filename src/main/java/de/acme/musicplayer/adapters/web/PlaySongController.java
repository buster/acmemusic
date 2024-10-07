package de.acme.musicplayer.adapters.web;

import de.acme.musicplayer.application.usecases.LiedAbspielenUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaySongController {

    private LiedAbspielenUseCase liedAbspielenUseCase;

    @PostMapping
    public void playSong(String id) {
        liedAbspielenUseCase.playSong(id);
    }
}
