package de.acme.musicplayer.applications.users.adapters.events;

import de.acme.musicplayer.applications.users.usecases.BenutzerIstTopScorerUsecase;
import de.acme.musicplayer.events.NeuerTopScorerEvent;
import de.acme.musicplayer.events.NeuesLiedWurdeAngelegt;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListeners {

    private final BenutzerIstTopScorerUsecase benutzerIstTopScorerUsecase;

    public UserEventListeners(BenutzerIstTopScorerUsecase benutzerIstTopScorerUsecase) {
        this.benutzerIstTopScorerUsecase = benutzerIstTopScorerUsecase;
    }

    @EventListener
    public void handleLiedAngelegt(NeuerTopScorerEvent event) {
        benutzerIstTopScorerUsecase.neuerTopScorerGefunden(event);

    }

}
