package de.acme.musicplayer.applications.users.domain;

import de.acme.musicplayer.applications.scoreboard.domain.events.BenutzerIstNeuerTopScorer;
import de.acme.musicplayer.applications.users.domain.events.BenutzerHatAuszeichnungAnAnderenNutzerVerloren;
import de.acme.musicplayer.applications.users.domain.events.BenutzerHatNeueAuszeichnungErhalten;
import de.acme.musicplayer.applications.users.domain.model.Auszeichnung;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.applications.users.ports.BenutzerPort;
import de.acme.musicplayer.applications.users.ports.UserEventPublisher;
import de.acme.musicplayer.applications.users.usecases.AuszeichnungFürNeueTopScorer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class AuszeichnungFürNeueTopScorerService implements AuszeichnungFürNeueTopScorer {

    private final BenutzerPort benutzerPort;
    private final UserEventPublisher userEventPublisher;

    public AuszeichnungFürNeueTopScorerService(BenutzerPort benutzerPort, UserEventPublisher userEventPublisher) {
        this.benutzerPort = benutzerPort;
        this.userEventPublisher = userEventPublisher;
    }

    @Override
    @Transactional
    public void vergebeAuszeichnungFürNeuenTopScorer(BenutzerIstNeuerTopScorer event) {
        Benutzer neuerTopScorer = benutzerPort.leseBenutzer(event.neuerTopScorer(), event.tenantId());
        log.info("New top scorer: {}  (event user id: {})", neuerTopScorer, event.neuerTopScorer());
        neuerTopScorer.getAuszeichnungen().add(Auszeichnung.MUSIC_LOVER_LOVER);
        benutzerPort.speichereBenutzer(neuerTopScorer, event.getTenant());
        userEventPublisher.publishEvent(new BenutzerHatNeueAuszeichnungErhalten(neuerTopScorer.getId(), neuerTopScorer.getName().benutzername, Auszeichnung.MUSIC_LOVER_LOVER, event.tenantId()));
        if (event.alterTopScorer() != null) {
            Benutzer alterTopScorer = benutzerPort.leseBenutzer(event.alterTopScorer(), event.tenantId());
            alterTopScorer.getAuszeichnungen().remove(Auszeichnung.MUSIC_LOVER_LOVER);
            benutzerPort.speichereBenutzer(alterTopScorer, event.tenantId());
            userEventPublisher.publishEvent(new BenutzerHatAuszeichnungAnAnderenNutzerVerloren(alterTopScorer.getId(), alterTopScorer.getName().benutzername, neuerTopScorer.getId(), neuerTopScorer.getName().benutzername, Auszeichnung.MUSIC_LOVER_LOVER, event.tenantId()));

        }
    }
}
