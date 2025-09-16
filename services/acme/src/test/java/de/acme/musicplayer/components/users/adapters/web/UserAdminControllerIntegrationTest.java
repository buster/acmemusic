package de.acme.musicplayer.components.users.adapters.web;

import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.users.usecases.BenutzerAdministrationUsecase;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserAdminController.class)
class UserAdminControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BenutzerAdministrationUsecase benutzerAdministrationUsecase;

    @Test
    void sollteUserAdminSeiteAnzeigen() throws Exception {
        String tenantId = UUID.randomUUID().toString();
        when(benutzerAdministrationUsecase.zähleBenutzer(any(TenantId.class))).thenReturn(5L);

        mockMvc.perform(get("/user-admin")
                        .cookie(new Cookie("tenantId", tenantId))
                        .header("HX-Request", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("htmx-responses/user-admin.html"))
                .andExpect(model().attribute("userCount", 5L));

        verify(benutzerAdministrationUsecase).zähleBenutzer(new TenantId(tenantId));
    }

    @Test
    void sollteBenutzerDatenbankLoeschen() throws Exception {
        String tenantId = UUID.randomUUID().toString();

        mockMvc.perform(post("/delete-user-database")
                        .cookie(new Cookie("tenantId", tenantId))
                        .header("HX-Request", "true"))
                .andExpect(status().isOk())
                .andExpect(content().string("Benutzerdatenbank gelöscht"));

        verify(benutzerAdministrationUsecase).löscheBenutzerDatenbank(new TenantId(tenantId));
    }

    @Test
    void sollteBenutzerEventsLoeschen() throws Exception {
        String tenantId = UUID.randomUUID().toString();

        mockMvc.perform(post("/delete-user-events")
                        .cookie(new Cookie("tenantId", tenantId))
                        .header("HX-Request", "true"))
                .andExpect(status().isOk())
                .andExpect(content().string("BenutzerEvents gelöscht"));

        verify(benutzerAdministrationUsecase).löscheBenutzerEvents(new TenantId(tenantId));
    }

    @Test
    void sollteOhneHtmxHeaderNichtFunktionieren() throws Exception {
        String tenantId = UUID.randomUUID().toString();
        when(benutzerAdministrationUsecase.zähleBenutzer(any(TenantId.class))).thenReturn(5L);

        mockMvc.perform(get("/user-admin")
                        .cookie(new Cookie("tenantId", tenantId)))
                .andExpect(status().isOk());
    }

    @Test
    void sollteOhneTenantIdCookieNichtFunktionieren() throws Exception {
        mockMvc.perform(get("/user-admin")
                        .header("HX-Request", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("htmx-responses/error-toast.html"))
                .andExpect(model().attribute("message", "Required cookie 'tenantId' for method parameter type String is not present"));
    }
}