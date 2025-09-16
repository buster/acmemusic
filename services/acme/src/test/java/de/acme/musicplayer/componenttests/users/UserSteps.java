package de.acme.musicplayer.componenttests.users;

import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.scoreboard.domain.events.BenutzerIstNeuerTopScorer;
import de.acme.musicplayer.components.users.domain.model.Auszeichnung;
import de.acme.musicplayer.components.users.domain.model.Benutzer;
import de.acme.musicplayer.components.users.usecases.BenutzerAdministrationUsecase;
import de.acme.musicplayer.components.users.usecases.BenutzerRegistrierenUsecase;
import de.acme.musicplayer.components.users.usecases.BenutzerWurdeNeuerTopScorer;
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
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Slf4j
public class UserSteps {

    private final Map<String, BenutzerId> benutzerToIdMap = new HashMap<>();
    @Autowired
    private BenutzerRegistrierenUsecase benutzerRegistrierenUsecase;
    @Autowired
    private BenutzerAdministrationUsecase benutzerAdministrationUsecase;
    @Autowired
    private BenutzerWurdeNeuerTopScorer benutzerWurdeNeuerTopScorer;

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
        benutzerAdministrationUsecase.löscheBenutzerDatenbank(tenantId);
        MDC.remove("tenantId");
    }

    private Benutzer fetchBenutzer(String benutzer) {
        BenutzerId benutzerId = benutzerToIdMap.get(benutzer);
        return benutzerAdministrationUsecase.leseBenutzer(benutzerId, tenantId);
    }

    @Und("folgende Benutzer:")
    public void folgendeBenutzer(DataTable dataTable) {
        dataTable.asMaps().forEach(benutzer -> {
            BenutzerId benutzerId = benutzerRegistrierenUsecase.registriereBenutzer(new BenutzerRegistrierenUsecase.BenutzerRegistrierenCommand(new Benutzer.Name(benutzer.get("Name")), new Benutzer.Passwort(benutzer.get("Passwort")), new Benutzer.Email(benutzer.get("Email")), tenantId));
            log.info("Benutzer {} registriert, ID: {}", benutzer.get("Name"), benutzerId);
            assertThat(benutzerId).isNotNull();
            benutzerToIdMap.put(benutzer.get("Name"), benutzerId);
        });
    }


    private String letzteFehlermeldung;

    @Wenn("der Benutzer {string} (der )sich mit dem Passwort {string} und der Email {string} registriert und angemeldet hat")
    public void derBenutzerAliceSichMitDemPasswortAbcUndDerEmailBlaLocalhostComRegistriertHat(String username, String password, String email) {
        try {
            BenutzerId benutzerId = benutzerRegistrierenUsecase.registriereBenutzer(
                    new BenutzerRegistrierenUsecase.BenutzerRegistrierenCommand(
                            new Benutzer.Name(username),
                            new Benutzer.Passwort(password),
                            new Benutzer.Email(email),
                            tenantId
                    )
            );
            benutzerToIdMap.put(username, benutzerId);
            letzteFehlermeldung = null;
        } catch (IllegalArgumentException ex) {
            letzteFehlermeldung = ex.getMessage();
        }
    }

    @Dann("schlägt die Registrierung fehl mit der Fehlermeldung {string}")
    public void schlaegtDieRegistrierungFehlMitDerFehlermeldung(String erwarteteFehlermeldung) {
        assertThat(letzteFehlermeldung).isEqualTo(erwarteteFehlermeldung);
    }

    @Dann("kennt der Service {int} Benutzer")
    public void kenntDerServiceBenutzer(int anzahl) {
        assertThat(benutzerAdministrationUsecase.zähleBenutzer(tenantId)).isEqualTo(anzahl);
    }

    @Dann("erhält der Benutzer {string} die Auszeichnung {string}")
    @Und("der Benutzer {string} erhält die Auszeichnung {string}")
    public void erhältDerBenutzerAliceDieAuszeichnungTopUploader(String benutzer, String auszeichnung) {
        Benutzer benutzerEntity = fetchBenutzer(benutzer);
        assertThat(benutzerEntity.getAuszeichnungen()).contains(Auszeichnung.valueOf(auszeichnung));
    }

    @Und("der Benutzer {string} erhält nicht die Auszeichnung {string}")
    public void derBenutzerBobErhältNichtDieAuszeichnungMUSIC_LOVER_LOVER(String benutzer, String auszeichnung) {
        Benutzer benutzerEntity = fetchBenutzer(benutzer);
        assertThat(benutzerEntity.getAuszeichnungen()).doesNotContain(Auszeichnung.valueOf(auszeichnung));
    }

    @Wenn("der Benutzer {string} den Benutzer {string} als TopScorer abgelöst hat")
    public void derBenutzerJohnDenBenutzerBobAlsTopScorerAbgelöstHat(String neuerTopscorer, String alterTopScorer) {
        BenutzerIstNeuerTopScorer neuerTopScorer = new BenutzerIstNeuerTopScorer(benutzerToIdMap.get(neuerTopscorer), benutzerToIdMap.get(alterTopScorer), tenantId);
        benutzerWurdeNeuerTopScorer.vergebeAuszeichnungFürNeuenTopScorer(neuerTopScorer);
    }
}
