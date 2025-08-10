package de.acme.musicplayer.components.musicplayer.adapters.events;

import de.acme.musicplayer.AbstractIntegrationTest;
import de.acme.musicplayer.components.musicplayer.domain.events.NeuesLiedWurdeAngelegt;
import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.LiedId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.common.events.EventDispatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles("test")
class MusicPlayerEventListenersIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    @Qualifier("musicPlayerEventListeners")
    private EventDispatcher eventDispatcher;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Test
    void sollteEventDispatcherInterfaceImplementieren() {
        assertThat(eventDispatcher).isNotNull();
        assertThat(eventDispatcher).isInstanceOf(EventDispatcher.class);
    }

    @Test
    void sollteEventErhaltenUndVerarbeiten() {
        LiedId liedId = new LiedId(UUID.randomUUID().toString());
        BenutzerId benutzerId = new BenutzerId(UUID.randomUUID().toString());
        TenantId tenantId = new TenantId(UUID.randomUUID().toString());
        NeuesLiedWurdeAngelegt event = new NeuesLiedWurdeAngelegt(liedId, benutzerId, tenantId);

        assertDoesNotThrow(() -> eventDispatcher.handleEvent(event));
    }

    @Test
    void sollteEventUeberSpringEventPublisherEmpfangen() {
        LiedId liedId = new LiedId(UUID.randomUUID().toString());
        BenutzerId benutzerId = new BenutzerId(UUID.randomUUID().toString());
        TenantId tenantId = new TenantId(UUID.randomUUID().toString());
        NeuesLiedWurdeAngelegt event = new NeuesLiedWurdeAngelegt(liedId, benutzerId, tenantId);

        assertDoesNotThrow(() -> eventPublisher.publishEvent(event));
    }
}