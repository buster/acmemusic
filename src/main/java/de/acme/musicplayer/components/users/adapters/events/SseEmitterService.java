package de.acme.musicplayer.components.users.adapters.events;

import de.acme.musicplayer.common.TenantId;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Stream;

@Service
@Slf4j
public class SseEmitterService {

    private final ConcurrentHashMap<Pair<String, String>, Set<SseEmitter>> emitters = new ConcurrentHashMap<>();

    public void addEmitter(SseEmitter emitter, String tenantId, String userId) {
        Set<SseEmitter> tenantEmitters = emitters.getOrDefault(Pair.of(tenantId, userId), new CopyOnWriteArraySet<>());
        tenantEmitters.add(emitter);
        emitters.put(Pair.of(tenantId, userId), tenantEmitters);
        emitter.onCompletion(() -> removeEmitter(emitter));
        emitter.onTimeout(() -> removeEmitter(emitter));
    }

    private void removeEmitter(SseEmitter emitter) {
        emitters.values().forEach(emitterSet -> emitterSet.remove(emitter));
    }

    public void sendEvent(TenantId tenant, String eventData) {
        emitterStreamOfTenant(tenant.value())
                .forEach(entry -> {
                    List<SseEmitter> errorEmitters = new ArrayList<>();
                    for (SseEmitter emitter : entry.getValue()) {
                        try {
                            log.info("Sending event to tenant: " + tenant.value());
                            log.info("event data: " + eventData);
                            emitter.send(SseEmitter.event()
                                    .name("message")
                                    .data(eventData));
                        } catch (Exception e) {
                            log.debug("Error sending event to tenant: {}", tenant.value(), e);
                            errorEmitters.add(emitter);
                            emitter.completeWithError(e);
                        }
                    }
                    errorEmitters.forEach(emitter -> entry.getValue().remove(emitter));
                });
    }

    private Stream<Map.Entry<Pair<String, String>, Set<SseEmitter>>> emitterStreamOfTenant(String tenant) {
        return emitters.entrySet().stream()
                .filter(pairSetEntry -> pairSetEntry.getKey().getLeft().equals(tenant));
    }

    public void sendEventToUser(String userId, TenantId tenant, String eventData) {
        emitterStreamOfTenant(tenant.value())
                .filter(pairSetEntry -> Objects.equals(pairSetEntry.getKey().getRight(), userId))
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
                            log.debug("Error sending event to tenant: " + tenant.value(), e);
                            errorEmitters.add(emitter);
                            emitter.complete();
                        }
                    }
                    errorEmitters.forEach(emitter -> pairSetEntry.getValue().remove(emitter));
                });
    }
}
