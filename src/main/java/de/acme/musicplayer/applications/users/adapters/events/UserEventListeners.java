package de.acme.musicplayer.applications.users.adapters.events;

import de.acme.musicplayer.applications.users.usecases.BenutzerIstTopScorerUsecase;
import de.acme.musicplayer.events.BenutzerHatNeueAuszeichnungEvent;
import de.acme.musicplayer.events.NeuerTopScorerEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserEventListeners {

    private final BenutzerIstTopScorerUsecase benutzerIstTopScorerUsecase;
    private final SseEmitterService sseEmitterService;

    public UserEventListeners(BenutzerIstTopScorerUsecase benutzerIstTopScorerUsecase, SseEmitterService sseEmitterService) {
        this.benutzerIstTopScorerUsecase = benutzerIstTopScorerUsecase;
        this.sseEmitterService = sseEmitterService;
    }

    @EventListener
    public void neuerTopScorer(NeuerTopScorerEvent event) {
        log.info("Listener: NeuerTopScorerEvent");
        benutzerIstTopScorerUsecase.neuerTopScorerGefunden(event);
    }

    @EventListener
    public void BenutzerHatNeueAuszeichnungEvent(BenutzerHatNeueAuszeichnungEvent event) {
        log.info("Listener: BenutzerHatNeueAuszeichnungEvent");
        log.info("Sende SSE Event für BenutzerHatNeueAuszeichnungEvent");
        String eventData = "<div>Benutzer " + event.id().Id() + " hat die Auszeichnung " + event.auszeichnung() + " erhalten</div>";
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
        sseEmitterService.sendEvent(event.getTenant(), event.getClass().getSimpleName(),
                eventDiv);
    }

}
