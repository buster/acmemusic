package de.acme.musicplayer.cucumber.users.test2test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.acme.musicplayer.applications.users.domain.model.Auszeichnung;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.applications.users.usecases.BenutzerAdministrationUsecase;
import de.acme.musicplayer.applications.users.usecases.BenutzerRegistrierenUsecase;
import de.acme.musicplayer.applications.users.usecases.UserEventDispatcher;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.Event;
import de.acme.musicplayer.common.TenantId;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private UserEventDispatcher userEventDispatcher;

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
        benutzerAdministrationUsecase.löscheDatenbank(tenantId);
        MDC.remove("tenantId");
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


    @Wenn("der Benutzer {string} (der )sich mit dem Passwort {string} und der Email {string} registriert hat")
    public void derBenutzerAliceSichMitDemPasswortAbcUndDerEmailBlaLocalhostComRegistriertHat(String username, String password, String email) {
        BenutzerId benutzerId = benutzerRegistrierenUsecase.registriereBenutzer(new BenutzerRegistrierenUsecase.BenutzerRegistrierenCommand(new Benutzer.Name(username), new Benutzer.Passwort(password), new Benutzer.Email(email), tenantId));
        benutzerToIdMap.put(username, benutzerId);
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

    private Benutzer fetchBenutzer(String benutzer) {
        BenutzerId benutzerId = benutzerToIdMap.get(benutzer);
        return benutzerAdministrationUsecase.leseBenutzer(benutzerId, tenantId);
    }

    @Wenn("das Ereignis {string} mit den folgenden Werten empfangen wird:")
    public void dasEreignisBenutzerIstNeuerTopScorerMitDenFolgendenWertenEmpfangenWird(String eventClass, DataTable dataTable) throws ClassNotFoundException, JsonProcessingException {
        Event event = buildEventFromStringAndDatatable(eventClass, dataTable);
        userEventDispatcher.handleEvent(event);
    }

    private Event buildEventFromStringAndDatatable(String eventClass, DataTable dataTable) throws JsonProcessingException, ClassNotFoundException {
        Map<String, String> values = dataTable.asMap(String.class, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder eventContent = new StringBuilder("{");
        for (Map.Entry<String, String> entry : values.entrySet()) {
            String value = replacePlaceholders(entry.getValue());
            eventContent.append(entry.getKey()).append(":").append(value).append(",");
        }
        eventContent.deleteCharAt(eventContent.length() - 1);
        eventContent.append("}");
        return (Event) objectMapper.readValue(eventContent.toString(), Class.forName("de.acme.musicplayer.applications.scoreboard.domain.events." + eventClass));
    }

    private String replacePlaceholders(String value) {
        Matcher benutzerIdMatcher = Pattern.compile("<BenutzerId:(.*)?>").matcher(value);
        if (benutzerIdMatcher.find()) {
            String benutzerName = benutzerIdMatcher.group(1);
            return benutzerIdMatcher.replaceAll(benutzerToIdMap.get(benutzerName.strip()).Id());
        } else if (value.contains("<tenantId>")) {
            return value.replace("<tenantId>", tenantId.value());
        }
        return value;
    }
}
