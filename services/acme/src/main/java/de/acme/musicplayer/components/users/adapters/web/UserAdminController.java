package de.acme.musicplayer.components.users.adapters.web;

import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.users.usecases.BenutzerAdministrationUsecase;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserAdminController {
    private final BenutzerAdministrationUsecase benutzerAdministrationUsecase;

    public UserAdminController(BenutzerAdministrationUsecase benutzerAdministrationUsecase) {
        this.benutzerAdministrationUsecase = benutzerAdministrationUsecase;
    }

    @HxRequest
    @GetMapping("/user-admin")
    public String userAdmin(Model model, @CookieValue(value = "tenantId", required = true) String tenantId) {
        model.addAttribute("userCount", benutzerAdministrationUsecase.zähleBenutzer(new TenantId(tenantId)));
        return "htmx-responses/user-admin.html";
    }

    @HxRequest
    @PostMapping("/delete-user-database")
    public ResponseEntity<String> deleteUserDatabase(@CookieValue(value = "tenantId", required = true) String tenantId) {
        benutzerAdministrationUsecase.löscheBenutzerDatenbank(new TenantId(tenantId));
        return ResponseEntity.ok("Benutzerdatenbank gelöscht");
    }

    @HxRequest
    @PostMapping("/delete-user-events")
    public ResponseEntity<String> deleteUserEvents(@CookieValue(value = "tenantId", required = true) String tenantId) {
        benutzerAdministrationUsecase.löscheBenutzerEvents(new TenantId(tenantId));
        return ResponseEntity.ok("BenutzerEvents gelöscht");
    }
}
