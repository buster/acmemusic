package de.acme.musicplayer.applications.users.domain;

import de.acme.musicplayer.applications.users.domain.model.Auszeichnung;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.applications.users.ports.BenutzerPort;
import de.acme.musicplayer.applications.users.usecases.BenutzerIstTopScorerUsecase;
import de.acme.musicplayer.applications.users.usecases.UserEventPublisher;
import de.acme.musicplayer.events.BenutzerHatAuszeichnungVerlorenEvent;
import de.acme.musicplayer.events.BenutzerHatNeueAuszeichnungEvent;
import de.acme.musicplayer.events.NeuerTopScorerEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class BenutzerIstTopScorerService implements BenutzerIstTopScorerUsecase {

    private final BenutzerPort benutzerPort;
    private final UserEventPublisher userEventPublisher;

    public BenutzerIstTopScorerService(BenutzerPort benutzerPort, UserEventPublisher userEventPublisher) {
        this.benutzerPort = benutzerPort;
        this.userEventPublisher = userEventPublisher;
    }

    @Override
    @Transactional
    public void neuerTopScorerGefunden(NeuerTopScorerEvent event) {
        Benutzer neuerTopScorer = benutzerPort.leseBenutzer(event.neuerTopScorer(), event.tenantId());
        log.info("New top scorer: {}  (event user id: {})", neuerTopScorer, event.neuerTopScorer());
        neuerTopScorer.getAuszeichnungen().add(Auszeichnung.MUSIC_LOVER_LOVER);
        userEventPublisher.publishEvent(new BenutzerHatNeueAuszeichnungEvent(neuerTopScorer.getId(), Auszeichnung.MUSIC_LOVER_LOVER, event.tenantId()));
        if (event.alterTopScorer() != null) {
            Benutzer alterTopScorer = benutzerPort.leseBenutzer(event.alterTopScorer(), event.tenantId());
            alterTopScorer.getAuszeichnungen().remove(Auszeichnung.MUSIC_LOVER_LOVER);
            userEventPublisher.publishEvent(new BenutzerHatAuszeichnungVerlorenEvent(alterTopScorer.getId(), Auszeichnung.MUSIC_LOVER_LOVER, event.tenantId()));

        }
    }
}
