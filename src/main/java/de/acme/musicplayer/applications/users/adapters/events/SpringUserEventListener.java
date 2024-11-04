package de.acme.musicplayer.applications.users.adapters.events;

import de.acme.musicplayer.applications.users.domain.events.BenutzerHatAuszeichnungAnAnderenNutzerVerloren;
import de.acme.musicplayer.applications.users.domain.events.BenutzerHatNeueAuszeichnungErhalten;
import de.acme.musicplayer.applications.users.usecases.UserEventDispatcher;
import de.acme.musicplayer.common.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpringUserEventListener {

    private final UserEventDispatcher userEventDispatcher;

    private final SseEmitterService sseEmitterService;

    public SpringUserEventListener(UserEventDispatcher userEventDispatcher, SseEmitterService sseEmitterService) {
        this.userEventDispatcher = userEventDispatcher;
        this.sseEmitterService = sseEmitterService;
    }

    @EventListener
    @Async
    public void neuerTopScorer(Event event) {
        log.info("Listener: {}", event.getClass().getSimpleName());
        userEventDispatcher.handleEvent(event);
    }

    @EventListener
    @Async
    public void BenutzerHatNeueAuszeichnungEvent(BenutzerHatNeueAuszeichnungErhalten event) {
        log.info("Listener: BenutzerHatNeueAuszeichnungEvent");
        log.info("Sende SSE Event für BenutzerHatNeueAuszeichnungEvent");
        String eventData = "<div>Benutzer " +
                event.benutzername() +
                " hat die Auszeichnung " +
                event.auszeichnung() +
                " erhalten</div>";
        String eventDiv = String.format("<div class=\"toast fade show\" role=\"alert\" aria-live=\"assertive\" aria-atomic=\"true\">\n" +
                "    <div class=\"toast-header\">\n" +
                "        <strong class=\"me-auto\">Event erhalten</strong>\n" +
                "        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"toast\" aria-label=\"Close\"></button>\n" +
                "    </div>\n" +
                "    <div class=\"toast-body\">\n" +
                "<span>" + eventData + "</span>" +
                "    </div>\n" +
                "</div>");
        sseEmitterService.sendEvent(event.getTenant(),
                eventDiv);
    }

    @EventListener
    @Async
    public void BenutzerHatAuszeichnungAnAnderenNutzerVerloren(BenutzerHatAuszeichnungAnAnderenNutzerVerloren event) {
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
