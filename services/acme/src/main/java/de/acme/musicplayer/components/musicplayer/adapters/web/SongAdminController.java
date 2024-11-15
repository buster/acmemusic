package de.acme.musicplayer.components.musicplayer.adapters.web;

import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.musicplayer.usecases.LiedAdministrationUsecase;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SongAdminController {
    private final LiedAdministrationUsecase liedAdministrationUsecase;

    public SongAdminController(LiedAdministrationUsecase liedAdministrationUsecase) {
        this.liedAdministrationUsecase = liedAdministrationUsecase;
    }

    @HxRequest
    @GetMapping("/song-admin")
    public String songAdmin() {
        return "htmx-responses/song-admin.html";
    }

    @HxRequest
    @PostMapping("/delete-song-database")
    public ResponseEntity<String> deleteUserDatabase(@CookieValue(value = "tenantId") String tenantId) {
        liedAdministrationUsecase.löscheLiedDatenbank(new TenantId(tenantId));
        return ResponseEntity.ok("Liederdatenbank gelöscht");
    }

    @HxRequest
    @PostMapping("/delete-song-events")
    public ResponseEntity<String> deleteUserEvents(@CookieValue(value = "tenantId") String tenantId) {
        liedAdministrationUsecase.löscheLiedEvents(new TenantId(tenantId));
        return ResponseEntity.ok("LiederEvents gelöscht");
    }
}
