package de.acme.musicplayer.adapters.web;

import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.usecases.LiedAbspielenUsecase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LiedAbspielenController {

    private LiedAbspielenUsecase liedAbspielenUseCase;

    @PostMapping
    public void liedAbspielen(String id) {
        liedAbspielenUseCase.spieleLiedAb(new Lied.Id(id));
    }
}
