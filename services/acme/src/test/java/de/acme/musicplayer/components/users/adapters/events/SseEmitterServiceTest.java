package de.acme.musicplayer.components.users.adapters.events;

import de.acme.musicplayer.common.api.TenantId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SseEmitterServiceTest {

    private SseEmitterService service;

    @BeforeEach
    void setUp() {
        service = new SseEmitterService();
    }

    @Test
    void sollteSseEmitterHinzufuegenKoennen() throws IOException {
        SseEmitter emitter = mock(SseEmitter.class);
        String tenantId = "tenant-1";
        String userId = "user-1";

        service.addEmitter(emitter, tenantId, userId);

        verify(emitter).onCompletion(any(Runnable.class));
    }

    @Test
    void sollteEmitterViaOnCompletionCallbackEntfernenKoennen() throws IOException {
        // Arrange
        SseEmitter emitter1 = mock(SseEmitter.class);
        SseEmitter emitter2 = mock(SseEmitter.class);
        String tenantId = "tenant-1";
        String userId = "user-1";

        ArgumentCaptor<Runnable> callbackCaptor = ArgumentCaptor.forClass(Runnable.class);

        // Add first emitter and capture onCompletion callback
        service.addEmitter(emitter1, tenantId, userId);
        verify(emitter1).onCompletion(callbackCaptor.capture());
        Runnable onCompletion1 = callbackCaptor.getValue();

        // Add second emitter
        service.addEmitter(emitter2, tenantId, userId);

        // Act - trigger onCompletion for first emitter
        onCompletion1.run();

        // Assert - verify that emitter1 no longer receives messages by checking
        // that sending to this user/tenant pair doesn't invoke send on the removed emitter
        reset(emitter1, emitter2);
        service.sendEventToUser(userId, new TenantId(tenantId), "test-data");

        verify(emitter1, never()).send(any(SseEmitter.SseEventBuilder.class));
        verify(emitter2).send(any(SseEmitter.SseEventBuilder.class));
    }

    @Test
    void sollteSseEmitterEventErfolgreichVersendendKoennen() throws IOException {
        // Arrange
        SseEmitter emitter = mock(SseEmitter.class);
        String tenantId = "tenant-1";
        String userId = "user-1";
        String eventData = "test-event-data";
        TenantId tenant = new TenantId(tenantId);

        ArgumentCaptor<Runnable> callbackCaptor = ArgumentCaptor.forClass(Runnable.class);
        service.addEmitter(emitter, tenantId, userId);
        verify(emitter).onCompletion(callbackCaptor.capture());

        // Act
        service.sendEventToUser(userId, tenant, eventData);

        // Assert
        verify(emitter).send(any(SseEmitter.SseEventBuilder.class));
    }

    @Test
    void sollteFehlerBeiVersendungHandhabenUndEmitterEntfernen() throws IOException {
        // Arrange
        SseEmitter errorEmitter = mock(SseEmitter.class);
        SseEmitter healthyEmitter = mock(SseEmitter.class);
        String tenantId = "tenant-1";
        String userId = "user-1";
        String eventData = "test-event-data";
        TenantId tenant = new TenantId(tenantId);

        ArgumentCaptor<Runnable> callbackCaptor = ArgumentCaptor.forClass(Runnable.class);

        // Setup: add error emitter that throws IOException on send
        service.addEmitter(errorEmitter, tenantId, userId);
        verify(errorEmitter).onCompletion(callbackCaptor.capture());
        doThrow(new IOException("Network error")).when(errorEmitter).send(any(SseEmitter.SseEventBuilder.class));

        // Setup: add healthy emitter
        service.addEmitter(healthyEmitter, tenantId, userId);
        verify(healthyEmitter).onCompletion(callbackCaptor.capture());

        // Act
        service.sendEventToUser(userId, tenant, eventData);

        // Assert - error emitter should be completed and removed
        verify(errorEmitter).complete();

        // Verify that on subsequent send, error emitter is not contacted again
        reset(errorEmitter, healthyEmitter);
        service.sendEventToUser(userId, tenant, eventData);
        verify(errorEmitter, never()).send(any(SseEmitter.SseEventBuilder.class));
        verify(healthyEmitter).send(any(SseEmitter.SseEventBuilder.class));
    }

    @Test
    void sollteMultipleEmitterFuerVerschiedeneUserVerwaltenKoennen() throws IOException {
        // Arrange
        SseEmitter user1Emitter = mock(SseEmitter.class);
        SseEmitter user2Emitter = mock(SseEmitter.class);
        String tenantId = "tenant-1";
        String user1Id = "user-1";
        String user2Id = "user-2";
        TenantId tenant = new TenantId(tenantId);

        service.addEmitter(user1Emitter, tenantId, user1Id);
        service.addEmitter(user2Emitter, tenantId, user2Id);

        // Act - send event to user1
        service.sendEventToUser(user1Id, tenant, "event-for-user1");

        // Assert - only user1 emitter should receive the event
        verify(user1Emitter).send(any(SseEmitter.SseEventBuilder.class));
        verify(user2Emitter, never()).send(any(SseEmitter.SseEventBuilder.class));
    }

    @Test
    void sollteMultipleEmitterFuerGleichenUserHandhabenKoennen() throws IOException {
        // Arrange
        SseEmitter emitter1 = mock(SseEmitter.class);
        SseEmitter emitter2 = mock(SseEmitter.class);
        String tenantId = "tenant-1";
        String userId = "user-1";
        String eventData = "test-data";
        TenantId tenant = new TenantId(tenantId);

        service.addEmitter(emitter1, tenantId, userId);
        service.addEmitter(emitter2, tenantId, userId);

        // Act
        service.sendEventToUser(userId, tenant, eventData);

        // Assert - both emitters should receive the event
        verify(emitter1).send(any(SseEmitter.SseEventBuilder.class));
        verify(emitter2).send(any(SseEmitter.SseEventBuilder.class));
    }

    @Test
    void sollteEmitterFuerVerschiedeneTenantsTrennenKoennen() throws IOException {
        // Arrange
        SseEmitter tenant1Emitter = mock(SseEmitter.class);
        SseEmitter tenant2Emitter = mock(SseEmitter.class);
        String user = "user-1";
        String tenant1Id = "tenant-1";
        String tenant2Id = "tenant-2";

        service.addEmitter(tenant1Emitter, tenant1Id, user);
        service.addEmitter(tenant2Emitter, tenant2Id, user);

        // Act - send event to tenant1
        service.sendEventToUser(user, new TenantId(tenant1Id), "event-data");

        // Assert - only tenant1 emitter should receive the event
        verify(tenant1Emitter).send(any(SseEmitter.SseEventBuilder.class));
        verify(tenant2Emitter, never()).send(any(SseEmitter.SseEventBuilder.class));
    }

    @Test
    void sollteKeineEmitterFindenWennKeineAngemeldetSind() {
        // Arrange
        String tenantId = "tenant-1";
        String userId = "user-1";
        TenantId tenant = new TenantId(tenantId);

        // Act & Assert - should not throw exception
        service.sendEventToUser(userId, tenant, "event-data");
    }

}
