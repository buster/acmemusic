package de.acme.musicplayer.applications.users.adapters.events;

import de.acme.musicplayer.common.TenantId;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
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

    private final ConcurrentHashMap<Pair<String, String>, Set<SseEmitter>> emitters = new ConcurrentHashMap<>();

    public void addEmitter(SseEmitter emitter, String tenantId, String userId) {
        Set<SseEmitter> tenantEmitters = emitters.getOrDefault(tenantId, new CopyOnWriteArraySet<>());
        tenantEmitters.add(emitter);
        emitters.put(Pair.of(tenantId, userId), tenantEmitters);
        emitter.onCompletion(() -> emitters.get(tenantId).remove(emitter));
        emitter.onTimeout(() -> emitters.get(tenantId).remove(emitter));
    }

    @Async
    public void sendEvent(TenantId tenant, String eventData) {
        emitters.entrySet().stream()
                .filter(pairSetEntry -> pairSetEntry.getKey().getLeft().equals(tenant.value()))
                .forEach(pairSetEntry -> {
                    List<SseEmitter> errorEmitters = new ArrayList<>();
                    for (SseEmitter emitter : pairSetEntry.getValue()) {
                        try {
                            log.info("Sending event to tenant: " + tenant.value());
                            log.info("event data: " + eventData);
                            emitter.send(SseEmitter.event()
                                    .name("message")
                                    .data(eventData));
                        } catch (Exception e) {
                            log.error("Error sending event to tenant: " + tenant.value(), e);
                            errorEmitters.add(emitter);
                            emitter.complete();
                        }
                    }
                    errorEmitters.forEach(emitter -> pairSetEntry.getValue().remove(emitter));
                });
    }

    @Async
    public void sendEventToUser(String userId, TenantId tenant, String eventData) {
        emitters.entrySet().stream()
                .filter(pairSetEntry -> pairSetEntry.getKey().getLeft().equals(tenant.value()))
                .filter(pairSetEntry -> pairSetEntry.getKey().getRight().equals(userId))
                .forEach(pairSetEntry -> {
                    List<SseEmitter> errorEmitters = new ArrayList<>();
                    for (SseEmitter emitter : pairSetEntry.getValue()) {
                        try {
                            log.info("Sending event to tenant: " + tenant.value());
                            log.info("event data: " + eventData);
                            emitter.send(SseEmitter.event()
                                    .name("message")
                                    .data(eventData));
                        } catch (Exception e) {
                            log.error("Error sending event to tenant: " + tenant.value(), e);
                            errorEmitters.add(emitter);
                            emitter.complete();
                        }
                    }
                    errorEmitters.forEach(emitter -> pairSetEntry.getValue().remove(emitter));
                });
    }
}
