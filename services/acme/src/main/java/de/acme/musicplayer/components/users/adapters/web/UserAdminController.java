package de.acme.musicplayer.components.users.adapters.web;

import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.users.domain.model.Benutzer;
import de.acme.musicplayer.components.users.usecases.BenutzerAdministrationUsecase;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserAdminController {
    private final BenutzerAdministrationUsecase benutzerAdministrationUsecase;

    public UserAdminController(BenutzerAdministrationUsecase benutzerAdministrationUsecase) {
        this.benutzerAdministrationUsecase = benutzerAdministrationUsecase;
    }

    @HxRequest
    @GetMapping("/user-admin")
    public String userAdmin(Model model, @CookieValue(value = "tenantId") String tenantId) {
        model.addAttribute("userCount", benutzerAdministrationUsecase.zähleBenutzer(new TenantId(tenantId)));
        return "htmx-responses/user-admin.html";
    }

    @HxRequest
    @PostMapping("/delete-user-database")
    public ResponseEntity<String> deleteUserDatabase(@CookieValue(value = "tenantId") String tenantId) {
        benutzerAdministrationUsecase.löscheDatenbank(new TenantId(tenantId));
        return ResponseEntity.ok("Benutzerdatenbank gelöscht");
    }

    @HxRequest
    @PostMapping("/delete-user-events")
    public ResponseEntity<String> deleteUserEvents(@CookieValue(value = "tenantId") String tenantId) {
        benutzerAdministrationUsecase.löscheEvents(new TenantId(tenantId));
        return ResponseEntity.ok("BenutzerEvents gelöscht");
    }

    @HxRequest
    @PostMapping("/read-user")
    public ResponseEntity<Benutzer> readUser(@CookieValue(value = "tenantId") String tenantId, @RequestParam("benutzerId") String benutzerId) {
        Benutzer benutzer = benutzerAdministrationUsecase.leseBenutzer(new BenutzerId(benutzerId), new TenantId(tenantId));
        return ResponseEntity.ok(benutzer);
    }
}
