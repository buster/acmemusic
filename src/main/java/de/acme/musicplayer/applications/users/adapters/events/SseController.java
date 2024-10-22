package de.acme.musicplayer.applications.users.adapters.events;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class SseController {

    private final SseEmitterService sseEmitterService;

    public SseController(SseEmitterService sseEmitterService) {
        this.sseEmitterService = sseEmitterService;
    }

    @GetMapping(path = "/users/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@CookieValue(value = "tenantId") String tenantId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        sseEmitterService.addEmitter(emitter, tenantId);
        return emitter;
    }
}
