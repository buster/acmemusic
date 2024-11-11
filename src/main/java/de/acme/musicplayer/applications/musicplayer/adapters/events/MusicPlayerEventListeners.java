package de.acme.musicplayer.applications.musicplayer.adapters.events;

import de.acme.musicplayer.events.NeuesLiedWurdeAngelegt;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MusicPlayerEventListeners {

    @EventListener
    public void handleLiedAngelegt(NeuesLiedWurdeAngelegt event) {
        System.out.println("Received event: " + event);
    }

}
