package de.acme.musicplayer.adapters.web;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Benutzer.Email;
import de.acme.musicplayer.application.domain.model.Benutzer.Name;
import de.acme.musicplayer.application.domain.model.Benutzer.Passwort;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.TenantId;
import de.acme.musicplayer.application.usecases.BenutzerRegistrierenUsecase;
import de.acme.musicplayer.application.usecases.BenutzerRegistrierenUsecase.BenutzerRegistrierenCommand;
import de.acme.musicplayer.application.usecases.LiedAbspielenUsecase;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.InputStream;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Controller
public class LiedAbspielenController {

    private final LiedAbspielenUsecase liedAbspielenUseCase;

    private final BenutzerRegistrierenUsecase benutzerRegistrierenUsecase;

    public LiedAbspielenController(LiedAbspielenUsecase liedAbspielenUseCase, BenutzerRegistrierenUsecase benutzerRegistrierenUsecase) {
        this.liedAbspielenUseCase = liedAbspielenUseCase;
        this.benutzerRegistrierenUsecase = benutzerRegistrierenUsecase;
    }

    @PostMapping
    public InputStream liedAbspielen(String benutzerId, String liedId, String tenantId) {
        return liedAbspielenUseCase.liedStreamen(new Benutzer.Id(benutzerId), new Lied.Id(liedId), new TenantId(tenantId));
    }

    @GetMapping("/")
    public String index(Model model, @RequestParam(required = false) String tenantId) {
        model.addAttribute("greeting", "Hello World!");
        if (isNotBlank(tenantId)) {
            model.addAttribute("tenantId", tenantId);
        }
        return "index.html";
    }

    @HxRequest
    @GetMapping("/registration-form")
    public String register(Model model) {
        model.addAttribute("greeting", "Hello World!");
        return "registration-form.html";
    }

    @HxRequest
    @PostMapping("/register-user")
    public ResponseEntity<Void> registerUser(String username, String email, String password, String tenantId) {
        benutzerRegistrierenUsecase.registriereBenutzer(new BenutzerRegistrierenCommand(
                new Name(username),
                new Passwort(password),
                new Email(email),
                new TenantId(tenantId)));
        return ResponseEntity.ok().build();
    }
}
