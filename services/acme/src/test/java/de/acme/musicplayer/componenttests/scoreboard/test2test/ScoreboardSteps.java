package de.acme.musicplayer.componenttests.scoreboard.test2test;

import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.LiedId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.common.events.Event;
import de.acme.musicplayer.common.events.EventPublisher;
import de.acme.musicplayer.components.musicplayer.domain.events.NeuesLiedWurdeAngelegt;
import de.acme.musicplayer.components.scoreboard.domain.events.BenutzerIstNeuerTopScorer;
import de.acme.musicplayer.components.scoreboard.usecases.ScoreBoardAdministrationUsecase;
import de.acme.musicplayer.components.scoreboard.usecases.ZähleNeueLiederUsecase;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Und;
import io.cucumber.java.de.Wenn;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Slf4j
public class ScoreboardSteps {

    private final Map<String, BenutzerId> benutzerToIdMap = new HashMap<>();
    @Autowired
    private ScoreBoardAdministrationUsecase scoreboardAdministrationUsecase;
    @Autowired
    private ZähleNeueLiederUsecase zähleNeueLiederUsecase;
    @Autowired
    private EventPublisher scoreboardEventPublisher;

    private TenantId tenantId;

    @Before
    public void generateTenantId() {
        tenantId = new TenantId(UUID.randomUUID().toString());
        MDC.put("tenantId", tenantId.value());
        log.info("TenantId: {}", tenantId);
    }

    @After
    public void cleanDatabaseAfterScenario() {
        log.info("Clean database after scenario  {}", tenantId);
        scoreboardAdministrationUsecase.löscheScoreboardDatenbank(tenantId);
        scoreboardAdministrationUsecase.löscheScoreboardEvents(tenantId);
        MDC.remove("tenantId");
    }


    @Und("folgende Benutzer:")
    public void folgendeBenutzer(DataTable dataTable) {
        dataTable.asMaps().forEach(benutzer -> {
            BenutzerId benutzerId = new BenutzerId(UUID.randomUUID().toString());
            log.info("Benutzer {} registriert, ID: {}", benutzer.get("Name"), benutzerId);
            assertThat(benutzerId).isNotNull();
            benutzerToIdMap.put(benutzer.get("Name"), benutzerId);
        });
    }

    @Wenn("der Benutzer {string} ein neues Lied namens {string} aus der Datei {string} hochgeladen hat")
    public void liedUploadMitTitelUndDatei(String benutzer, String titel, String datei) {
        liedUploadMitBenutzer(benutzer);
    }

    @Wenn("der Benutzer {string} ein neues Lied hochgeladen hat")
    public void liedUploadMitBenutzer(String benutzerName) {
        BenutzerId benutzerId = benutzerToIdMap.get(benutzerName);
        assertThat(benutzerId).isNotNull();
        LiedId liedId = new LiedId(UUID.randomUUID().toString());
        log.info("Benutzer {} hat ein neues Lied hochgeladen, ID: {}", benutzerName, liedId);
        assertThat(liedId).isNotNull();
        NeuesLiedWurdeAngelegt neuesLiedWurdeAngelegt = new NeuesLiedWurdeAngelegt(liedId, benutzerId, tenantId);

        zähleNeueLiederUsecase.zähleNeueAngelegteLieder(neuesLiedWurdeAngelegt);
    }

    @Dann("ist der Benutzer {string} neuer TopScorer geworden")
    public void istDerBenutzerBobNeuerTopScorerGeworden(String benutzerName) {
        List<Event> events = scoreboardEventPublisher.readEventsFromOutbox(Integer.MAX_VALUE, tenantId).stream()
                .filter(event -> event instanceof BenutzerIstNeuerTopScorer)
                .filter(event -> ((BenutzerIstNeuerTopScorer) event).neuerTopScorer().equals(benutzerToIdMap.get(benutzerName)))
                .toList();
        scoreboardEventPublisher.removeEventsFromOutbox(events);
    }

    @Dann("ist der Benutzer {string} neuer TopScorer geworden und hat {string} abgelöst")
    public void istDerBenutzerAliceNeuerTopScorerGewordenUndHatBobAbgelöst(String neuerTopScorer, String abgelösterTopScorer) {
        List<Event> events = scoreboardEventPublisher.readEventsFromOutbox(Integer.MAX_VALUE, tenantId).stream()
                .filter(event -> event instanceof BenutzerIstNeuerTopScorer)
                .filter(event -> ((BenutzerIstNeuerTopScorer) event).neuerTopScorer().equals(benutzerToIdMap.get(neuerTopScorer)) &&
                        ((BenutzerIstNeuerTopScorer) event).alterTopScorer().equals(benutzerToIdMap.get(abgelösterTopScorer))
                )
                .toList();
        scoreboardEventPublisher.removeEventsFromOutbox(events);
    }

}
