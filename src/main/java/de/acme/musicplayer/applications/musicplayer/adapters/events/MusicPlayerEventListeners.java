package de.acme.musicplayer.applications.musicplayer.adapters.events;

import de.acme.musicplayer.applications.musicplayer.domain.events.NeuesLiedWurdeAngelegt;
import de.acme.musicplayer.applications.musicplayer.usecases.MusicplayerEventDispatcher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MusicPlayerEventListeners {

    private final MusicplayerEventDispatcher musicplayerEventDispatcher;

    public MusicPlayerEventListeners(MusicplayerEventDispatcher musicplayerEventDispatcher) {
        this.musicplayerEventDispatcher = musicplayerEventDispatcher;
    }


    @EventListener
    public void handleLiedAngelegt(NeuesLiedWurdeAngelegt event) {
        musicplayerEventDispatcher.handleEvent(event);
    }


}
