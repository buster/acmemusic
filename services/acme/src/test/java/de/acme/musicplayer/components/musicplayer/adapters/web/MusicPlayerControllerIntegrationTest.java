package de.acme.musicplayer.components.musicplayer.adapters.web;

import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.LiedId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.musicplayer.domain.model.Lied;
import de.acme.musicplayer.components.musicplayer.usecases.LiedAbspielenUsecase;
import de.acme.musicplayer.components.musicplayer.usecases.LiedHochladenUsecase;
import de.acme.musicplayer.components.musicplayer.usecases.LiederAuflistenUsecase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.servlet.http.Cookie;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MusicPlayerController.class)
class MusicPlayerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LiedAbspielenUsecase liedAbspielenUsecase;

    @MockBean
    private LiedHochladenUsecase liedHochladenUsecase;

    @MockBean
    private LiederAuflistenUsecase liederAuflistenUsecase;

    @Test
    void sollteStreamSongEndpointBereitstellen() throws Exception {
        // Arrange
        String liedId = UUID.randomUUID().toString();
        String tenantId = UUID.randomUUID().toString();
        InputStream testStream = new ByteArrayInputStream("test audio data".getBytes());
        
        when(liedAbspielenUsecase.liedStreamen(any(LiedId.class), any(TenantId.class)))
            .thenReturn(testStream);

        // Act & Assert
        mockMvc.perform(get("/streamSong")
                .param("liedId", liedId)
                .cookie(new Cookie("tenantId", tenantId)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM));
    }

    @Test
    void sollteUploadSongEndpointBereitstellen() throws Exception {
        // Arrange
        String userId = UUID.randomUUID().toString();
        String tenantId = UUID.randomUUID().toString();
        String titel = "Test Song";
        LiedId liedId = new LiedId(UUID.randomUUID().toString());
        MockMultipartFile file = new MockMultipartFile("file", "test.mp3", "audio/mpeg", "test content".getBytes());

        when(liedHochladenUsecase.liedHochladen(any(BenutzerId.class), any(Lied.Titel.class), any(InputStream.class), any(TenantId.class)))
            .thenReturn(liedId);

        // Act & Assert
        mockMvc.perform(multipart("/uploadSong")
                .file(file)
                .param("titel", titel)
                .cookie(new Cookie("userId", userId))
                .cookie(new Cookie("tenantId", tenantId))
                .header("HX-Request", "true"))
            .andExpect(status().isOk())
            .andExpect(view().name("htmx-responses/song-upload-successfull-toast.html"))
            .andExpect(model().attribute("titel", titel))
            .andExpect(model().attribute("file", "test.mp3"))
            .andExpect(model().attribute("songId", liedId.id()));
    }

    @Test
    void sollteFileUploadFormEndpointBereitstellen() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/fileUploadForm")
                .header("HX-Request", "true"))
            .andExpect(status().isOk())
            .andExpect(view().name("htmx-responses/song-upload.html"));
    }

    @Test
    void sollteSonglistEndpointBereitstellen() throws Exception {
        // Arrange
        String tenantId = UUID.randomUUID().toString();
        String benutzerId = UUID.randomUUID().toString();
        Lied testLied = Lied.neuesLied(new Lied.Titel("Test Song"), new BenutzerId(benutzerId), new TenantId(tenantId));
        
        when(liederAuflistenUsecase.liederAuflisten(any(TenantId.class), any(BenutzerId.class)))
            .thenReturn(List.of(testLied));

        // Act & Assert
        mockMvc.perform(get("/songlist")
                .cookie(new Cookie("tenantId", tenantId))
                .cookie(new Cookie("userId", benutzerId)))
            .andExpect(status().isOk())
            .andExpect(view().name("htmx-responses/songlist.html"))
            .andExpect(model().attribute("lieder", List.of(testLied)))
            .andExpect(model().attribute("userId", benutzerId))
            .andExpect(model().attribute("tenantId", tenantId));
    }
}