package de.acme.musicplayer.components.users.domain;

import de.acme.musicplayer.common.TenantId;
import de.acme.musicplayer.common.events.EventPublisher;
import de.acme.musicplayer.components.scoreboard.domain.events.BenutzerIstNeuerTopScorer;
import de.acme.musicplayer.components.users.domain.events.BenutzerHatAuszeichnungAnAnderenNutzerVerloren;
import de.acme.musicplayer.components.users.domain.events.BenutzerHatNeueAuszeichnungErhalten;
import de.acme.musicplayer.components.users.domain.model.Auszeichnung;
import de.acme.musicplayer.components.users.domain.model.Benutzer;
import de.acme.musicplayer.components.users.ports.BenutzerPort;
import de.acme.musicplayer.components.users.usecases.BenutzerWurdeNeuerTopScorer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class BenutzerWurdeNeuerTopScorerService implements BenutzerWurdeNeuerTopScorer {

    private final BenutzerPort benutzerPort;
    private final EventPublisher userEventPublisher;

    public BenutzerWurdeNeuerTopScorerService(BenutzerPort benutzerPort, EventPublisher userEventPublisher) {
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
        TenantId tenantId = event.getTenant();
        userEventPublisher.publishEvent(new BenutzerHatNeueAuszeichnungErhalten(neuerTopScorer.getId(), neuerTopScorer.getName().benutzername, Auszeichnung.MUSIC_LOVER_LOVER, event.tenantId()), tenantId);
        if (event.alterTopScorer() != null) {
            Benutzer alterTopScorer = benutzerPort.leseBenutzer(event.alterTopScorer(), event.tenantId());
            alterTopScorer.getAuszeichnungen().remove(Auszeichnung.MUSIC_LOVER_LOVER);
            benutzerPort.speichereBenutzer(alterTopScorer, event.tenantId());
            userEventPublisher.publishEvent(new BenutzerHatAuszeichnungAnAnderenNutzerVerloren(alterTopScorer.getId(), alterTopScorer.getName().benutzername, neuerTopScorer.getId(), neuerTopScorer.getName().benutzername, Auszeichnung.MUSIC_LOVER_LOVER, event.tenantId()), tenantId);

        }
    }
}
