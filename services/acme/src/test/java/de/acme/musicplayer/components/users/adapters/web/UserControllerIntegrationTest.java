package de.acme.musicplayer.components.users.adapters.web;

import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.users.domain.model.Benutzer;
import de.acme.musicplayer.components.users.usecases.BenutzerRegistrierenUsecase;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BenutzerRegistrierenUsecase benutzerRegistrierenUsecase;

    @Test
    void sollteRegistrierungsFormularAnzeigen() throws Exception {
        mockMvc.perform(get("/registration-form")
                        .header("HX-Request", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("htmx-responses/user-registration-form.html"))
                .andExpect(model().attribute("greeting", "Hello World!"));
    }

    @Test
    void sollteBenutzerRegistrieren() throws Exception {
        String tenantId = UUID.randomUUID().toString();
        String benutzerId = UUID.randomUUID().toString();
        
        when(benutzerRegistrierenUsecase.registriereBenutzer(any(BenutzerRegistrierenUsecase.BenutzerRegistrierenCommand.class)))
                .thenReturn(new BenutzerId(benutzerId));

        mockMvc.perform(post("/register-user")
                        .param("username", "testuser")
                        .param("email", "test@example.com")
                        .param("password", "testpass")
                        .cookie(new Cookie("tenantId", tenantId))
                        .header("HX-Request", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("htmx-responses/user-registration-successfull-toast.html"))
                .andExpect(model().attribute("userId", benutzerId))
                .andExpect(model().attribute("userName", "testuser"))
                .andExpect(cookie().value("userId", benutzerId))
                .andExpect(cookie().httpOnly("userId", true));

        verify(benutzerRegistrierenUsecase).registriereBenutzer(any(BenutzerRegistrierenUsecase.BenutzerRegistrierenCommand.class));
    }

    @Test
    void sollteBenutzerRegistrierungMitKorrektenParameternAufrufen() throws Exception {
        String tenantId = UUID.randomUUID().toString();
        String benutzerId = UUID.randomUUID().toString();
        
        when(benutzerRegistrierenUsecase.registriereBenutzer(any(BenutzerRegistrierenUsecase.BenutzerRegistrierenCommand.class)))
                .thenReturn(new BenutzerId(benutzerId));

        mockMvc.perform(post("/register-user")
                        .param("username", "alice")
                        .param("email", "alice@example.com")
                        .param("password", "secret123")
                        .cookie(new Cookie("tenantId", tenantId))
                        .header("HX-Request", "true"))
                .andExpect(status().isOk());

        verify(benutzerRegistrierenUsecase).registriereBenutzer(
                argThat(command ->
                        "alice".equals(command.name().benutzername) &&
                        "secret123".equals(command.passwort().passwort) &&
                        "alice@example.com".equals(command.email().email) &&
                        tenantId.equals(command.tenant().value())
                )
        );
    }

    @Test
    void sollteOhneHtmxHeaderNichtFunktionieren() throws Exception {
        mockMvc.perform(get("/registration-form"))
                .andExpect(status().isOk());
    }

    @Test
    void sollteOhneTenantIdCookieNichtFunktionieren() throws Exception {
        mockMvc.perform(post("/register-user")
                        .param("username", "testuser")
                        .param("email", "test@example.com")
                        .param("password", "testpass")
                        .header("HX-Request", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("htmx-responses/error-toast.html"))
                .andExpect(model().attribute("message", "Required cookie 'tenantId' for method parameter type String is not present"));
    }

    @Test
    void sollteOhneParameterNichtFunktionieren() throws Exception {
        String tenantId = UUID.randomUUID().toString();

        mockMvc.perform(post("/register-user")
                        .cookie(new Cookie("tenantId", tenantId))
                        .header("HX-Request", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("htmx-responses/error-toast.html"))
                .andExpect(model().attribute("message", "Benutzername darf nicht leer sein"));
    }
}