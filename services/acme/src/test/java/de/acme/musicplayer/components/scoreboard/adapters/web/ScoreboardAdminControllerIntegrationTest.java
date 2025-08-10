package de.acme.musicplayer.components.scoreboard.adapters.web;

import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.scoreboard.usecases.ScoreBoardAdministrationUsecase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.servlet.http.Cookie;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ScoreboardAdminController.class)
class ScoreboardAdminControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScoreBoardAdministrationUsecase scoreBoardAdministrationUsecase;

    @Test
    void sollteScoreboardAdminSeiteAnzeigen() throws Exception {
        mockMvc.perform(get("/scoreboard-admin")
                        .header("HX-Request", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("htmx-responses/scoreboard-admin.html"));
    }

    @Test
    void sollteScoreboardDatenbankLoeschen() throws Exception {
        String tenantId = UUID.randomUUID().toString();

        mockMvc.perform(post("/delete-scoreboard-database")
                        .cookie(new Cookie("tenantId", tenantId))
                        .header("HX-Request", "true"))
                .andExpect(status().isOk())
                .andExpect(content().string("Scoreboarddatenbank gelöscht"));

        verify(scoreBoardAdministrationUsecase).löscheScoreboardDatenbank(new TenantId(tenantId));
    }

    @Test
    void sollteScoreboardEventsLoeschen() throws Exception {
        String tenantId = UUID.randomUUID().toString();

        mockMvc.perform(post("/delete-scoreboard-events")
                        .cookie(new Cookie("tenantId", tenantId))
                        .header("HX-Request", "true"))
                .andExpect(status().isOk())
                .andExpect(content().string("ScoreboardEvents gelöscht"));

        verify(scoreBoardAdministrationUsecase).löscheScoreboardEvents(new TenantId(tenantId));
    }

    @Test
    void sollteOhneHtmxHeaderNichtFunktionieren() throws Exception {
        mockMvc.perform(get("/scoreboard-admin"))
                .andExpect(status().isOk());
    }

    @Test
    void sollteOhneTenantIdCookieNichtFunktionierenBeiDatenbankLoeschung() throws Exception {
        mockMvc.perform(post("/delete-scoreboard-database")
                        .header("HX-Request", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("htmx-responses/error-toast.html"))
                .andExpect(model().attribute("message", "Required cookie 'tenantId' for method parameter type String is not present"));
    }

    @Test
    void sollteOhneTenantIdCookieNichtFunktionierenBeiEventsLoeschung() throws Exception {
        mockMvc.perform(post("/delete-scoreboard-events")
                        .header("HX-Request", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("htmx-responses/error-toast.html"))
                .andExpect(model().attribute("message", "Required cookie 'tenantId' for method parameter type String is not present"));
    }

    @Test
    void sollteOhneHtmxHeaderBeiDatenbankLoeschungFehlerAnzeigen() throws Exception {
        String tenantId = UUID.randomUUID().toString();

        mockMvc.perform(post("/delete-scoreboard-database")
                        .cookie(new Cookie("tenantId", tenantId)))
                .andExpect(status().isOk())
                .andExpect(view().name("htmx-responses/error-toast.html"))
                .andExpect(model().attribute("message", "No static resource delete-scoreboard-database."));
    }

    @Test
    void sollteOhneHtmxHeaderBeiEventsLoeschungFehlerAnzeigen() throws Exception {
        String tenantId = UUID.randomUUID().toString();

        mockMvc.perform(post("/delete-scoreboard-events")
                        .cookie(new Cookie("tenantId", tenantId)))
                .andExpect(status().isOk())
                .andExpect(view().name("htmx-responses/error-toast.html"))
                .andExpect(model().attribute("message", "No static resource delete-scoreboard-events."));
    }
}