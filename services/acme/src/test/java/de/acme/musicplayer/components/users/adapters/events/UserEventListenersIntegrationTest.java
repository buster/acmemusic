package de.acme.musicplayer.components.users.adapters.events;

import de.acme.musicplayer.AbstractIntegrationTest;
import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.common.events.Event;
import de.acme.musicplayer.components.scoreboard.domain.events.BenutzerIstNeuerTopScorer;
import de.acme.musicplayer.components.users.domain.events.BenutzerHatAuszeichnungAnAnderenNutzerVerloren;
import de.acme.musicplayer.components.users.domain.events.BenutzerHatNeueAuszeichnungErhalten;
import de.acme.musicplayer.components.users.domain.model.Auszeichnung;
import de.acme.musicplayer.components.users.usecases.BenutzerWurdeNeuerTopScorer;
import de.acme.musicplayer.components.users.adapters.events.SseEmitterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
class UserEventListenersIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private UserEventListeners userEventListeners;

    @MockBean
    private BenutzerWurdeNeuerTopScorer benutzerWurdeNeuerTopScorer;

    @MockBean
    private SseEmitterService sseEmitterService;

    private TenantId tenantId;
    private BenutzerId benutzerId;
    private BenutzerId andereBenutzerId;

    @BeforeEach
    void setUp() {
        tenantId = new TenantId(UUID.randomUUID().toString());
        benutzerId = new BenutzerId(UUID.randomUUID().toString());
        andereBenutzerId = new BenutzerId(UUID.randomUUID().toString());
    }

    @Test
    void sollteBenutzerIstNeuerTopScorerEventBehandeln() {
        BenutzerIstNeuerTopScorer event = new BenutzerIstNeuerTopScorer(benutzerId, andereBenutzerId, tenantId);

        userEventListeners.handleEvent(event);

        verify(benutzerWurdeNeuerTopScorer).vergebeAuszeichnungFürNeuenTopScorer(event);
    }

    @Test
    void sollteBenutzerHatNeueAuszeichnungErhaltenEventBehandeln() {
        BenutzerHatNeueAuszeichnungErhalten event = new BenutzerHatNeueAuszeichnungErhalten(
                benutzerId, "testuser", Auszeichnung.MUSIC_LOVER_LOVER, tenantId);

        userEventListeners.handleEvent(event);

        verify(sseEmitterService).sendEvent(any(TenantId.class), any(String.class));
    }

    @Test
    void sollteBenutzerHatAuszeichnungAnAnderenNutzerVerlorenEventBehandeln() {
        BenutzerHatAuszeichnungAnAnderenNutzerVerloren event = new BenutzerHatAuszeichnungAnAnderenNutzerVerloren(
                benutzerId, "testuser", andereBenutzerId, "neuerBesitzer", Auszeichnung.MUSIC_LOVER_LOVER, tenantId);

        userEventListeners.handleEvent(event);

        verify(sseEmitterService).sendEventToUser(any(String.class), any(TenantId.class), any(String.class));
    }

    @Test
    void sollteBenutzerHatNeueAuszeichnungErhaltenEventMitKorrektenParameternSenden() {
        BenutzerHatNeueAuszeichnungErhalten event = new BenutzerHatNeueAuszeichnungErhalten(
                benutzerId, "alice", Auszeichnung.MUSIC_LOVER_LOVER, tenantId);

        userEventListeners.handleEvent(event);

        verify(sseEmitterService).sendEvent(tenantId, 
                "<div class=\"toast fade show\" role=\"alert\" aria-live=\"assertive\" aria-atomic=\"true\">\n" +
                "    <div class=\"toast-header\">\n" +
                "        <strong class=\"me-auto\">Event erhalten</strong>\n" +
                "        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"toast\" aria-label=\"Close\"></button>\n" +
                "    </div>\n" +
                "    <div class=\"toast-body\">\n" +
                "<span><div>Benutzer alice hat die Auszeichnung MUSIC_LOVER_LOVER erhalten</div></span>" +
                "    </div>\n" +
                "</div>");
    }

    @Test
    void sollteBenutzerHatAuszeichnungVerlorenEventMitKorrektenParameternSenden() {
        BenutzerHatAuszeichnungAnAnderenNutzerVerloren event = new BenutzerHatAuszeichnungAnAnderenNutzerVerloren(
                benutzerId, "alice", andereBenutzerId, "bob", Auszeichnung.MUSIC_LOVER_LOVER, tenantId);

        userEventListeners.handleEvent(event);

        verify(sseEmitterService).sendEventToUser(benutzerId.Id(), tenantId,
                "<div class=\"toast fade show\" role=\"alert\" aria-live=\"assertive\" aria-atomic=\"true\">\n" +
                "    <div class=\"toast-header\">\n" +
                "        <strong class=\"me-auto\">Event erhalten</strong>\n" +
                "        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"toast\" aria-label=\"Close\"></button>\n" +
                "    </div>\n" +
                "    <div class=\"toast-body\">\n" +
                "<span><div>Du hast die Auszeichnung MUSIC_LOVER_LOVER an bob verloren!</div></span>" +
                "    </div>\n" +
                "</div>");
    }

    @Test
    void sollteUnbekannteEventsIgnorieren() {
        Event unbekannterEvent = new Event() {
            @Override
            public TenantId getTenant() {
                return tenantId;
            }
        };

        assertThatCode(() -> {
            userEventListeners.handleEvent(unbekannterEvent);
        }).doesNotThrowAnyException();

        verify(benutzerWurdeNeuerTopScorer, org.mockito.Mockito.never()).vergebeAuszeichnungFürNeuenTopScorer(any());
        verify(sseEmitterService, org.mockito.Mockito.never()).sendEvent(any(), any());
        verify(sseEmitterService, org.mockito.Mockito.never()).sendEventToUser(any(), any(), any());
    }
}