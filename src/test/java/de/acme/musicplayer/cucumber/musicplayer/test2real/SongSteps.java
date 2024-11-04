package de.acme.musicplayer.cucumber.musicplayer.test2real;

import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedAbspielenUsecase;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedAdministrationUsecase;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedHochladenUsecase;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.TenantId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Gegebenseien;
import io.cucumber.java.de.Und;
import io.cucumber.java.de.Wenn;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Slf4j
public class SongSteps {

    private final Map<String, LiedId> titelToIdMap = new HashMap<>();
    private final Map<String, BenutzerId> benutzerToIdMap = new HashMap<>();
    @Autowired
    private LiedAdministrationUsecase liedAdministrationUsecase;
    @Autowired
    private LiedHochladenUsecase liedHochladenUseCase;
    @Autowired
    private LiedAbspielenUsecase liedAbspielenUsecase;

    private long lastReadSongSize;
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
        liedAdministrationUsecase.löscheDatenbank(tenantId);
        MDC.remove("tenantId");
    }

    @Gegebenseien("folgende Songs:")
    public void folgendeSongs(DataTable dataTable) throws URISyntaxException, IOException {
        for (Map<String, String> eintrag : dataTable.asMaps()) {
            String titel = eintrag.get("Titel");
            try (InputStream inputStream = new FileInputStream(new File(ClassLoader.getSystemResource(eintrag.get("Dateiname")).toURI()))) {
                LiedId liedId = liedHochladenUseCase.liedHochladen(benutzerToIdMap.get(eintrag.get("Benutzer")), new Lied.Titel(titel), inputStream, tenantId);
                log.info("Song {} hochgeladen, ID: {}", titel, liedId);
                assertThat(liedId).isNotNull();
                titelToIdMap.put(titel, liedId);
            }
        }
    }

    @Und("folgende Benutzer:")
    public void folgendeBenutzer(DataTable dataTable) {
        dataTable.asMaps().forEach(benutzer -> {
            benutzerToIdMap.put(benutzer.get("Name"), new BenutzerId(UUID.randomUUID().toString()));
        });
    }


    @Wenn("der Benutzer {string} (der )sich mit dem Passwort {string} und der Email {string} registriert hat")
    public void derBenutzerAliceSichMitDemPasswortAbcUndDerEmailBlaLocalhostComRegistriertHat(String username, String password, String email) {
        benutzerToIdMap.put(username, new BenutzerId(UUID.randomUUID().toString()));
    }

    @Wenn("der Benutzer {string} das Lied {string} abspielt")
    public void derBenutzerAliceDasLiedEpicSongAbspielt(String benutzer, String lied) throws IOException {
        try (InputStream inputStream = liedAbspielenUsecase.liedStreamen(titelToIdMap.get(lied), tenantId)) {
            lastReadSongSize = inputStream.readAllBytes().length;
        }
    }

    @Dann("erhält der Benutzer den Song {string} mit mehr als {long} Sekunden Länge")
    public void erhältDerBenutzerDenSongEpicSongMitMehrAlsMegabyteGröße(String titel, long size) {
        assertThat(lastReadSongSize).isGreaterThan(size);
    }

    @Und("der Benutzer {string} lädt das Lied mit dem Titel {string} aus der Datei {string} hoch")
    public void derBenutzerLädtDasLiedHoch(String benutzer, String titel, String datei) throws IOException, URISyntaxException {
        try (InputStream inputStream = new FileInputStream(new File(ClassLoader.getSystemResource(datei).toURI()))) {
            LiedId liedId = liedHochladenUseCase.liedHochladen(benutzerToIdMap.get(benutzer), new Lied.Titel(titel), inputStream, tenantId);
            log.info("Song {} hoch geladen, ID: {}", titel, liedId);
            assertThat(liedId).isNotNull();
            titelToIdMap.put(titel, liedId);
        }
    }

}
