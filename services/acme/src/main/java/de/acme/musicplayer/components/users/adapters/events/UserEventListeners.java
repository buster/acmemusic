package de.acme.musicplayer.components.users.adapters.events;

import de.acme.musicplayer.common.events.Event;
import de.acme.musicplayer.common.events.EventDispatcher;
import de.acme.musicplayer.components.scoreboard.domain.events.BenutzerIstNeuerTopScorer;
import de.acme.musicplayer.components.users.domain.events.BenutzerHatAuszeichnungAnAnderenNutzerVerloren;
import de.acme.musicplayer.components.users.domain.events.BenutzerHatNeueAuszeichnungErhalten;
import de.acme.musicplayer.components.users.usecases.BenutzerWurdeNeuerTopScorer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("userEventDispatcher")
@Slf4j
public class UserEventListeners implements EventDispatcher {

    private final BenutzerWurdeNeuerTopScorer benutzerWurdeNeuerTopScorer;
    private final SseEmitterService sseEmitterService;

    public UserEventListeners(BenutzerWurdeNeuerTopScorer benutzerWurdeNeuerTopScorer, SseEmitterService sseEmitterService) {
        this.benutzerWurdeNeuerTopScorer = benutzerWurdeNeuerTopScorer;
        this.sseEmitterService = sseEmitterService;
    }

    @Override
    @EventListener
//    @Async
    public void handleEvent(Event event) {
        log.info("Listener: {}", event.getClass().getSimpleName());
        if (event instanceof BenutzerIstNeuerTopScorer benutzerIstNeuerTopScorer) {
            benutzerWurdeNeuerTopScorer.vergebeAuszeichnungFürNeuenTopScorer(benutzerIstNeuerTopScorer);
        } else if (event instanceof BenutzerHatNeueAuszeichnungErhalten benutzerHatNeueAuszeichnungErhalten) {
            BenutzerHatNeueAuszeichnungEvent(benutzerHatNeueAuszeichnungErhalten);
        } else if (event instanceof BenutzerHatAuszeichnungAnAnderenNutzerVerloren benutzerHatAuszeichnungAnAnderenNutzerVerloren) {
            BenutzerHatAuszeichnungAnAnderenNutzerVerloren(benutzerHatAuszeichnungAnAnderenNutzerVerloren);
        }
    }

    private void BenutzerHatNeueAuszeichnungEvent(BenutzerHatNeueAuszeichnungErhalten event) {
        log.info("Listener: BenutzerHatNeueAuszeichnungEvent");
        log.info("Sende SSE Event für BenutzerHatNeueAuszeichnungEvent");
        String eventData = "<div>Benutzer " +
                event.benutzername() +
                " hat die Auszeichnung " +
                event.auszeichnung() +
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

        sseEmitterService.sendEventToUser(event.benutzerId().Id(), event.getTenant(), eventDiv);
    }

    private void BenutzerHatAuszeichnungAnAnderenNutzerVerloren(BenutzerHatAuszeichnungAnAnderenNutzerVerloren event) {
        log.info("Listener: BenutzerHatAuszeichnungAnAnderenNutzerVerloren");
        log.info("Sende SSE Event für BenutzerHatAuszeichnungAnAnderenNutzerVerloren");
        String eventData = "<div>Du hast die Auszeichnung " +
                event.auszeichnung() +
                " an " +
                event.neuerBesitzerName() +
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
        sseEmitterService.sendEventToUser(event.benutzerId().Id(), event.getTenant(),
                eventDiv);
    }

}
