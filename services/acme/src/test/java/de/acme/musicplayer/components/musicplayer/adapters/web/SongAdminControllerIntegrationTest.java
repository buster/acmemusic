package de.acme.musicplayer.components.musicplayer.adapters.web;

import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.musicplayer.usecases.LiedAdministrationUsecase;
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

@WebMvcTest(SongAdminController.class)
class SongAdminControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LiedAdministrationUsecase liedAdministrationUsecase;

    @Test
    void sollteSongAdminEndpointBereitstellen() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/song-admin")
                .header("HX-Request", "true"))
            .andExpect(status().isOk())
            .andExpect(view().name("htmx-responses/song-admin.html"));
    }

    @Test
    void sollteDeleteSongDatabaseEndpointBereitstellen() throws Exception {
        // Arrange
        String tenantId = UUID.randomUUID().toString();

        // Act & Assert
        mockMvc.perform(post("/delete-song-database")
                .cookie(new Cookie("tenantId", tenantId))
                .header("HX-Request", "true"))
            .andExpect(status().isOk())
            .andExpect(content().string("Liederdatenbank gelöscht"));

        verify(liedAdministrationUsecase).löscheLiedDatenbank(any(TenantId.class));
    }

    @Test
    void sollteDeleteSongEventsEndpointBereitstellen() throws Exception {
        // Arrange
        String tenantId = UUID.randomUUID().toString();

        // Act & Assert
        mockMvc.perform(post("/delete-song-events")
                .cookie(new Cookie("tenantId", tenantId))
                .header("HX-Request", "true"))
            .andExpect(status().isOk())
            .andExpect(content().string("LiederEvents gelöscht"));

        verify(liedAdministrationUsecase).löscheLiedEvents(any(TenantId.class));
    }

    @Test
    void sollteControllerKorrektInjiziert() throws Exception {
        // Assert
        mockMvc.perform(get("/song-admin")
                .header("HX-Request", "true"))
            .andExpect(status().isOk());
    }
}