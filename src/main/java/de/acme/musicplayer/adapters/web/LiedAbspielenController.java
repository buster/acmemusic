package de.acme.musicplayer.adapters.web;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.usecases.LiedAbspielenUsecase;
import de.acme.musicplayer.application.domain.model.TenantId;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.InputStream;

@Controller
public class LiedAbspielenController {

    private final LiedAbspielenUsecase liedAbspielenUseCase;

    public LiedAbspielenController(LiedAbspielenUsecase liedAbspielenUseCase) {
        this.liedAbspielenUseCase = liedAbspielenUseCase;
    }

    @PostMapping
    public InputStream liedAbspielen(String benutzerId, String liedId, String tenantId) {
        return liedAbspielenUseCase.liedStreamen(new Benutzer.Id(benutzerId), new Lied.Id(liedId), new TenantId(tenantId));
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("greeting", "Hello World!");
        return "index.html";
    }

    @HxRequest
    @GetMapping("/registration-form")
    public String register(Model model) {
        model.addAttribute("greeting", "Hello World!");
        return "registration-form.html";
    }
}
