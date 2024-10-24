package de.acme.musicplayer.applications.users.adapters.events;

import de.acme.musicplayer.applications.scoreboard.domain.events.BenutzerIstNeuerTopScorer;
import de.acme.musicplayer.applications.users.domain.events.BenutzerHatNeueAuszeichnungErhalten;
import de.acme.musicplayer.applications.users.usecases.AuszeichnungFürNeueTopScorer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserEventListeners {

    private final AuszeichnungFürNeueTopScorer auszeichnungFürNeueTopScorer;
    private final SseEmitterService sseEmitterService;

    public UserEventListeners(AuszeichnungFürNeueTopScorer auszeichnungFürNeueTopScorer, SseEmitterService sseEmitterService) {
        this.auszeichnungFürNeueTopScorer = auszeichnungFürNeueTopScorer;
        this.sseEmitterService = sseEmitterService;
    }

    @EventListener
    public void neuerTopScorer(BenutzerIstNeuerTopScorer event) {
        log.info("Listener: NeuerTopScorerEvent");
        auszeichnungFürNeueTopScorer.vergebeAuszeichnungFürNeuenTopScorer(event);
    }

    @EventListener
    public void BenutzerHatNeueAuszeichnungEvent(BenutzerHatNeueAuszeichnungErhalten event) {
        log.info("Listener: BenutzerHatNeueAuszeichnungEvent");
        log.info("Sende SSE Event für BenutzerHatNeueAuszeichnungEvent");
        String eventData = "<div>Benutzer " + event.benutzerId().Id() + " hat die Auszeichnung " + event.auszeichnung() + " erhalten</div>";
        String eventDiv = String.format("<div class=\"toast fade show\" role=\"alert\" aria-live=\"assertive\" aria-atomic=\"true\">\n" +
                "    <div class=\"toast-header\">\n" +
                "        <strong class=\"me-auto\">Event erhalten</strong>\n" +
                "        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"toast\" aria-label=\"Close\"></button>\n" +
                "    </div>\n" +
                "    <div class=\"toast-body\">\n" +
                        eventData +
                "        <input type=\"hidden\" id=\"userId\" name=\"userId\" th:value=\"${userId}\">\n" +
                "    </div>\n" +
                "</div>");
        sseEmitterService.sendEvent(event.getTenant(),
                eventDiv);
    }

}
