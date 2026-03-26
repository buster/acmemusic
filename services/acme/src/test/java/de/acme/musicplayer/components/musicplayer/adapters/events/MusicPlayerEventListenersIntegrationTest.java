package de.acme.musicplayer.components.musicplayer.adapters.events;

import de.acme.musicplayer.AbstractIntegrationTest;
import de.acme.musicplayer.components.musicplayer.domain.events.NeuesLiedWurdeAngelegt;
import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.LiedId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.users.domain.model.Benutzer;
import de.acme.musicplayer.components.users.ports.BenutzerPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
    private MusicPlayerEventListeners musicPlayerEventListeners;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private BenutzerPort benutzerPort;

    private void erstelleTestBenutzer(BenutzerId benutzerId, TenantId tenantId) {
        benutzerPort.benutzerHinzufügen(
            new Benutzer(benutzerId, new Benutzer.Name("testuser"), new Benutzer.Passwort("testpass"), new Benutzer.Email("test@test.de")),
            tenantId
        );
    }

    @Test
    void sollteEventListenerComponentSein() {
        assertThat(musicPlayerEventListeners).isNotNull();
    }

    @Test
    void sollteEventErhaltenUndVerarbeiten() {
        LiedId liedId = new LiedId(UUID.randomUUID().toString());
        BenutzerId benutzerId = new BenutzerId(UUID.randomUUID().toString());
        TenantId tenantId = new TenantId(UUID.randomUUID().toString());
        erstelleTestBenutzer(benutzerId, tenantId);
        NeuesLiedWurdeAngelegt event = new NeuesLiedWurdeAngelegt(liedId, benutzerId, tenantId);

        assertDoesNotThrow(() -> musicPlayerEventListeners.handleNeuesLiedWurdeAngelegt(event));
    }

    @Test
    void sollteEventUeberSpringEventPublisherEmpfangen() {
        LiedId liedId = new LiedId(UUID.randomUUID().toString());
        BenutzerId benutzerId = new BenutzerId(UUID.randomUUID().toString());
        TenantId tenantId = new TenantId(UUID.randomUUID().toString());
        erstelleTestBenutzer(benutzerId, tenantId);
        NeuesLiedWurdeAngelegt event = new NeuesLiedWurdeAngelegt(liedId, benutzerId, tenantId);

        assertDoesNotThrow(() -> eventPublisher.publishEvent(event));
    }
}
