package de.acme.musicplayer.adapters.web;

import de.acme.musicplayer.application.usecases.PlaySongUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaySongController {

    private PlaySongUseCase playSongUseCase;

    @PostMapping
    public void playSong(String id) {
        playSongUseCase.playSong(id);
    }
}
