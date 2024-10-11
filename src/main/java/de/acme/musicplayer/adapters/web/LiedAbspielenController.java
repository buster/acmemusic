package de.acme.musicplayer.adapters.web;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.usecases.LiedAbspielenUsecase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
public class LiedAbspielenController {

    private final LiedAbspielenUsecase liedAbspielenUseCase;

    public LiedAbspielenController(LiedAbspielenUsecase liedAbspielenUseCase) {
        this.liedAbspielenUseCase = liedAbspielenUseCase;
    }

    @PostMapping
    public InputStream liedAbspielen(String benutzerId, String liedId) {
        return liedAbspielenUseCase.liedStreamen(new Benutzer.Id(benutzerId), new Lied.Id(liedId));
    }
}
