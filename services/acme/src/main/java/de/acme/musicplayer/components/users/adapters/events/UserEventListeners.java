package de.acme.musicplayer.components.users.adapters.events;

import de.acme.musicplayer.components.scoreboard.domain.events.BenutzerIstNeuerTopScorer;
import de.acme.musicplayer.components.users.domain.events.BenutzerHatAuszeichnungAnAnderenNutzerVerloren;
import de.acme.musicplayer.components.users.domain.events.BenutzerHatNeueAuszeichnungErhalten;
import de.acme.musicplayer.components.users.usecases.BenutzerWurdeNeuerTopScorer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.web.util.HtmlUtils;

@Component("userEventDispatcher")
@Slf4j
public class UserEventListeners {

    private final BenutzerWurdeNeuerTopScorer benutzerWurdeNeuerTopScorer;
    private final SseEmitterService sseEmitterService;

    public UserEventListeners(BenutzerWurdeNeuerTopScorer benutzerWurdeNeuerTopScorer, SseEmitterService sseEmitterService) {
        this.benutzerWurdeNeuerTopScorer = benutzerWurdeNeuerTopScorer;
        this.sseEmitterService = sseEmitterService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void onBenutzerIstNeuerTopScorer(BenutzerIstNeuerTopScorer event) {
        log.info("Listener: {}", event.getClass().getSimpleName());
        benutzerWurdeNeuerTopScorer.vergebeAuszeichnungFürNeuenTopScorer(event);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void onBenutzerHatNeueAuszeichnung(BenutzerHatNeueAuszeichnungErhalten event) {
        log.info("Listener: BenutzerHatNeueAuszeichnungEvent");
        log.info("Sende SSE Event für BenutzerHatNeueAuszeichnungEvent");
        String eventData = "<div>Benutzer " +
                HtmlUtils.htmlEscape(event.benutzername()) +
                " hat die Auszeichnung " +
                HtmlUtils.htmlEscape(event.auszeichnung().toString()) +
                " erhalten</div>";
        String eventDiv = "<div class=\"toast fade show\" role=\"alert\" aria-live=\"assertive\" aria-atomic=\"true\">\n" +
                "    <div class=\"toast-header\">\n" +
                "        <strong class=\"me-auto\">Event erhalten</strong>\n" +
                "        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"toast\" aria-label=\"Close\"></button>\n" +
                "    </div>\n" +
                "    <div class=\"toast-body\">\n" +
                "<span>" +
                eventData +
                "</span>" +
                "    </div>\n" +
                "</div>";

        sseEmitterService.sendEventToUser(event.benutzerId().id(), event.getTenant(), eventDiv);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void onBenutzerHatAuszeichnungVerloren(BenutzerHatAuszeichnungAnAnderenNutzerVerloren event) {
        log.info("Listener: BenutzerHatAuszeichnungAnAnderenNutzerVerloren");
        log.info("Sende SSE Event für BenutzerHatAuszeichnungAnAnderenNutzerVerloren");
        String eventData = "<div>Du hast die Auszeichnung " +
                HtmlUtils.htmlEscape(event.auszeichnung().toString()) +
                " an " +
                HtmlUtils.htmlEscape(event.neuerBesitzerName()) +
                " verloren!</div>";
        String eventDiv = String.format("<div class=\"toast fade show\" role=\"alert\" aria-live=\"assertive\" aria-atomic=\"true\">\n" +
                "    <div class=\"toast-header\">\n" +
                "        <strong class=\"me-auto\">Event erhalten</strong>\n" +
                "        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"toast\" aria-label=\"Close\"></button>\n" +
                "    </div>\n" +
                "    <div class=\"toast-body\">\n" +
                "<span>" + eventData + "</span>" +
                "    </div>\n" +
                "</div>");
        sseEmitterService.sendEventToUser(event.benutzerId().id(), event.getTenant(),
                eventDiv);
    }

}
