package de.acme.musicplayer.applications.users.adapters.events;

import de.acme.musicplayer.common.TenantId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
@Slf4j
public class SseEmitterService {

    private final ConcurrentHashMap<String, Set<SseEmitter>> emitters = new ConcurrentHashMap<>();

    public void addEmitter(SseEmitter emitter, String tenantId) {
        Set<SseEmitter> tenantEmitters = emitters.getOrDefault(tenantId, new CopyOnWriteArraySet<>());
        tenantEmitters.add(emitter);
        emitters.put(tenantId, tenantEmitters);
        emitter.onCompletion(() -> emitters.get(tenantId).remove(emitter));
        emitter.onTimeout(() -> emitters.get(tenantId).remove(emitter));
    }

    @Async
    public void sendEvent(TenantId tenant, String eventData) {
        if (!emitters.containsKey(tenant.value())) {
            log.info("No emitters for tenant: " + tenant.value());
            return;
        }
        List<SseEmitter> errorEmitters = new ArrayList<>();
        for (SseEmitter emitter : emitters.get(tenant.value())) {
            try {
                log.info("Sending event to tenant: " + tenant.value());
                log.info("event data: " + eventData);
                emitter.send(SseEmitter.event()
                        .name("message")
                        .data(eventData));
            } catch (Exception e) {
                log.error("Error sending event to tenant: " + tenant.value(), e);
                errorEmitters.add(emitter);
//                emitter.complete();
            }
        }
        errorEmitters.forEach(emitter -> emitters.get(tenant.value()).remove(emitter));
    }
}
