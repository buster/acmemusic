package de.acme.musicplayer.components.scoreboard.adapters.events;

import de.acme.musicplayer.AbstractIntegrationTest;
import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.LiedId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.common.events.Event;
import de.acme.musicplayer.common.events.EventDispatcher;
import de.acme.musicplayer.components.musicplayer.domain.events.NeuesLiedWurdeAngelegt;
import de.acme.musicplayer.components.users.domain.model.Benutzer;
import de.acme.musicplayer.components.users.ports.BenutzerPort;
import org.junit.jupiter.api.BeforeEach;
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
class ScoreBoardEventListenersIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    @Qualifier("scoreBoardEventListeners")
    private EventDispatcher eventDispatcher;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private BenutzerPort benutzerPort;

    private TenantId tenantId;
    private BenutzerId benutzerId;
    private LiedId liedId;

    @BeforeEach
    void setUp() {
        tenantId = new TenantId(UUID.randomUUID().toString());
        benutzerId = new BenutzerId(UUID.randomUUID().toString());
        liedId = new LiedId(UUID.randomUUID().toString());
        
        // Benutzer muss in der DB existieren, da die synchrone Event-Chain
        // (NeuesLiedWurdeAngelegt → ZaehleNeueLieder → BenutzerIstNeuerTopScorer → BenutzerWurdeNeuerTopScorerService)
        // den Benutzer aus der DB liest
        benutzerPort.benutzerHinzufügen(
            new Benutzer(benutzerId, new Benutzer.Name("testuser"), new Benutzer.Passwort("testpass"), new Benutzer.Email("test@test.de")),
            tenantId
        );
    }

    @Test
    void sollteEventDispatcherInterfaceImplementieren() {
        assertThat(eventDispatcher).isNotNull();
        assertThat(eventDispatcher).isInstanceOf(EventDispatcher.class);
    }

    @Test
    void sollteNeuesLiedWurdeAngelegtEventBehandeln() {
        NeuesLiedWurdeAngelegt event = new NeuesLiedWurdeAngelegt(liedId, benutzerId, tenantId);

        assertDoesNotThrow(() -> eventDispatcher.handleEvent(event));
    }

    @Test
    void sollteEventUeberSpringEventPublisherEmpfangen() {
        NeuesLiedWurdeAngelegt event = new NeuesLiedWurdeAngelegt(liedId, benutzerId, tenantId);

        assertDoesNotThrow(() -> eventPublisher.publishEvent(event));
    }

    @Test
    void sollteMehrereNeuesLiedWurdeAngelegtEventsBehandelnKoennen() {
        NeuesLiedWurdeAngelegt event1 = new NeuesLiedWurdeAngelegt(liedId, benutzerId, tenantId);
        NeuesLiedWurdeAngelegt event2 = new NeuesLiedWurdeAngelegt(new LiedId(UUID.randomUUID().toString()), benutzerId, tenantId);

        assertDoesNotThrow(() -> {
            eventDispatcher.handleEvent(event1);
            eventDispatcher.handleEvent(event2);
        });
    }

    @Test
    void sollteUnbekannteEventsIgnorieren() {
        Event unbekannterEvent = new Event() {
            @Override
            public TenantId getTenant() {
                return tenantId;
            }
        };

        assertDoesNotThrow(() -> eventDispatcher.handleEvent(unbekannterEvent));
    }
}