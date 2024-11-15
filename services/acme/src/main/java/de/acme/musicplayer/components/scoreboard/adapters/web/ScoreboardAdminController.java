package de.acme.musicplayer.components.scoreboard.adapters.web;

import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.scoreboard.usecases.ScoreBoardAdministrationUsecase;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ScoreboardAdminController {
    private final ScoreBoardAdministrationUsecase scoreBoardAdministrationUsecase;

    public ScoreboardAdminController(ScoreBoardAdministrationUsecase scoreBoardAdministrationUsecase) {
        this.scoreBoardAdministrationUsecase = scoreBoardAdministrationUsecase;
    }

    @HxRequest
    @GetMapping("/scoreboard-admin")
    public String scoreboardAdmin() {
        return "htmx-responses/scoreboard-admin.html";
    }

    @HxRequest
    @PostMapping("/delete-scoreboard-database")
    public ResponseEntity<String> deleteUserDatabase(@CookieValue(value = "tenantId") String tenantId) {
        scoreBoardAdministrationUsecase.löscheScoreboardDatenbank(new TenantId(tenantId));
        return ResponseEntity.ok("Scoreboarddatenbank gelöscht");
    }

    @HxRequest
    @PostMapping("/delete-scoreboard-events")
    public ResponseEntity<String> deleteUserEvents(@CookieValue(value = "tenantId") String tenantId) {
        scoreBoardAdministrationUsecase.löscheScoreboardEvents(new TenantId(tenantId));
        return ResponseEntity.ok("ScoreboardEvents gelöscht");
    }
}
