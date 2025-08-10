package de.acme.musicplayer.components.users.adapters.events;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SseController.class)
class SseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SseEmitterService sseEmitterService;

    @Test
    void sollteSSEEndpointMitTenantIdUndUserIdAufrufenKoennen() throws Exception {
        String testTenantId = "tenant123";
        String testUserId = "user456";

        mockMvc.perform(get("/users/sse")
                        .cookie(new Cookie("tenantId", testTenantId))
                        .cookie(new Cookie("userId", testUserId)))
                .andExpect(status().isOk());

        verify(sseEmitterService).addEmitter(any(SseEmitter.class), eq(testTenantId), eq(testUserId));
    }

    @Test
    void sollteSSEEndpointMitNurTenantIdAufrufenKoennen() throws Exception {
        String testTenantId = "tenant123";

        mockMvc.perform(get("/users/sse")
                        .cookie(new Cookie("tenantId", testTenantId)))
                .andExpect(status().isOk());

        verify(sseEmitterService).addEmitter(any(SseEmitter.class), eq(testTenantId), eq(null));
    }

    @Test
    void sollteSSEEndpointOhneTenantIdFehlerWerfen() throws Exception {
        mockMvc.perform(get("/users/sse"))
                .andExpect(status().isOk())
                .andExpect(view().name("htmx-responses/error-toast.html"));
    }

    @Test
    void sollteServiceMethodeAufrufenBeiValidenCookies() throws Exception {
        String testTenantId = "tenant123";
        String testUserId = "user456";

        mockMvc.perform(get("/users/sse")
                        .cookie(new Cookie("tenantId", testTenantId))
                        .cookie(new Cookie("userId", testUserId)))
                .andExpect(status().isOk());

        verify(sseEmitterService).addEmitter(any(SseEmitter.class), eq(testTenantId), eq(testUserId));
    }

    @Test
    void sollteServiceMethodeAufrufenBeiNullUserId() throws Exception {
        String testTenantId = "tenant123";

        mockMvc.perform(get("/users/sse")
                        .cookie(new Cookie("tenantId", testTenantId)))
                .andExpect(status().isOk());

        verify(sseEmitterService).addEmitter(any(SseEmitter.class), eq(testTenantId), eq(null));
    }
}