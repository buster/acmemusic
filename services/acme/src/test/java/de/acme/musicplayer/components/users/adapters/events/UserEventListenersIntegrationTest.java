package de.acme.musicplayer.components.users.adapters.events;

import de.acme.musicplayer.AbstractIntegrationTest;
import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.scoreboard.domain.events.BenutzerIstNeuerTopScorer;
import de.acme.musicplayer.components.users.domain.events.BenutzerHatAuszeichnungAnAnderenNutzerVerloren;
import de.acme.musicplayer.components.users.domain.events.BenutzerHatNeueAuszeichnungErhalten;
import de.acme.musicplayer.components.users.domain.model.Auszeichnung;
import de.acme.musicplayer.components.users.usecases.BenutzerWurdeNeuerTopScorer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
class UserEventListenersIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private UserEventListeners userEventListeners;

    @MockitoBean
    private BenutzerWurdeNeuerTopScorer benutzerWurdeNeuerTopScorer;

    @MockitoBean
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

        userEventListeners.onBenutzerIstNeuerTopScorer(event);

        verify(benutzerWurdeNeuerTopScorer, timeout(2000)).vergebeAuszeichnungFürNeuenTopScorer(event);
    }

    @Test
    void sollteBenutzerHatNeueAuszeichnungErhaltenEventBehandeln() {
        BenutzerHatNeueAuszeichnungErhalten event = new BenutzerHatNeueAuszeichnungErhalten(
                benutzerId, "testuser", Auszeichnung.MUSIC_LOVER_LOVER, tenantId);

        userEventListeners.onBenutzerHatNeueAuszeichnung(event);

        verify(sseEmitterService, timeout(2000)).sendEventToUser(eq(event.benutzerId().id()), eq(tenantId), any(String.class));
    }

    @Test
    void sollteBenutzerHatAuszeichnungAnAnderenNutzerVerlorenEventBehandeln() {
        BenutzerHatAuszeichnungAnAnderenNutzerVerloren event = new BenutzerHatAuszeichnungAnAnderenNutzerVerloren(
                benutzerId, "testuser", andereBenutzerId, "neuerBesitzer", Auszeichnung.MUSIC_LOVER_LOVER, tenantId);

        userEventListeners.onBenutzerHatAuszeichnungVerloren(event);

        verify(sseEmitterService, timeout(2000)).sendEventToUser(any(String.class), any(TenantId.class), any(String.class));
    }

    @Test
    void sollteBenutzerHatNeueAuszeichnungErhaltenEventMitKorrektenParameternSenden() {
        BenutzerHatNeueAuszeichnungErhalten event = new BenutzerHatNeueAuszeichnungErhalten(
                benutzerId, "alice", Auszeichnung.MUSIC_LOVER_LOVER, tenantId);

        userEventListeners.onBenutzerHatNeueAuszeichnung(event);

        verify(sseEmitterService, timeout(2000)).sendEventToUser(eq(benutzerId.id()), eq(tenantId), eq(
                "<div class=\"toast fade show\" role=\"alert\" aria-live=\"assertive\" aria-atomic=\"true\">\n" +
                "    <div class=\"toast-header\">\n" +
                "        <strong class=\"me-auto\">Event erhalten</strong>\n" +
                "        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"toast\" aria-label=\"Close\"></button>\n" +
                "    </div>\n" +
                "    <div class=\"toast-body\">\n" +
                "<span><div>Benutzer alice hat die Auszeichnung MUSIC_LOVER_LOVER erhalten</div></span>" +
                "    </div>\n" +
                        "</div>"));
    }

    @Test
    void sollteBenutzerHatAuszeichnungVerlorenEventMitKorrektenParameternSenden() {
        BenutzerHatAuszeichnungAnAnderenNutzerVerloren event = new BenutzerHatAuszeichnungAnAnderenNutzerVerloren(
                benutzerId, "alice", andereBenutzerId, "bob", Auszeichnung.MUSIC_LOVER_LOVER, tenantId);

        userEventListeners.onBenutzerHatAuszeichnungVerloren(event);

        verify(sseEmitterService, timeout(2000)).sendEventToUser(benutzerId.id(), tenantId,
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
}
