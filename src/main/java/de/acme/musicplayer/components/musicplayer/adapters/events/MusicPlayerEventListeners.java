package de.acme.musicplayer.components.musicplayer.adapters.events;

import de.acme.musicplayer.components.musicplayer.domain.events.NeuesLiedWurdeAngelegt;
import de.acme.musicplayer.components.musicplayer.usecases.MusicplayerEventDispatcher;
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
