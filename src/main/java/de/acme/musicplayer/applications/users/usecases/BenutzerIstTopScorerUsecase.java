package de.acme.musicplayer.applications.users.usecases;

import de.acme.musicplayer.events.NeuerTopScorerEvent;

public interface BenutzerIstTopScorerUsecase {
    void neuerTopScorerGefunden(NeuerTopScorerEvent event);
}
