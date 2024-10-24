package de.acme.musicplayer.cucumber.test2real;

import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.domain.model.LiedAuszeichnung;
import de.acme.musicplayer.applications.musicplayer.domain.model.Playlist;
import de.acme.musicplayer.applications.musicplayer.usecases.*;
import de.acme.musicplayer.applications.scoreboard.usecases.ScoreBoardAdministrationUsecase;
import de.acme.musicplayer.applications.users.domain.model.Auszeichnung;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.applications.users.usecases.BenutzerAdministrationUsecase;
import de.acme.musicplayer.applications.users.usecases.BenutzerRegistrierenUsecase;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.PlaylistId;
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

@Slf4j
public class SongSteps {

    private final Map<String, LiedId> titelToIdMap = new HashMap<>();
    private final Map<String, BenutzerId> benutzerToIdMap = new HashMap<>();
    private final Map<String, PlaylistId> playlistToIdMap = new HashMap<>();
    @Autowired
    private BenutzerRegistrierenUsecase benutzerRegistrierenUsecase;
    @Autowired
    private BenutzerAdministrationUsecase benutzerAdministrationUsecase;
    @Autowired
    private LiedAdministrationUsecase liedAdministrationUsecase;
    @Autowired
    private LiedHochladenUsecase liedHochladenUseCase;
    @Autowired
    private LiedZuPlaylistHinzufügenUsecase liedZuPlaylistHinzufügenUseCase;
    @Autowired
    private LiederInPlaylistAuflistenUsecase liederInPlaylistAuflistenUseCase;
    @Autowired
    private PlaylistAnlegenUsecase playlistAnlegenUsecase;
    @Autowired
    private PlaylistAdministrationUsecase playlistAdministrationUsecase;
    @Autowired
    private LiedAbspielenUsecase liedAbspielenUsecase;
    @Autowired
    private ScoreBoardAdministrationUsecase scoreboardAdministrationUsecase;

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
        playlistAdministrationUsecase.löscheDatenbank(tenantId);
        liedAdministrationUsecase.löscheDatenbank(tenantId);
        benutzerAdministrationUsecase.löscheDatenbank(tenantId);
        scoreboardAdministrationUsecase.löscheDatenbank(tenantId);
        MDC.remove("tenantId");
    }

    @Wenn("sich der Benutzer {string} mit dem Passwort {string} und der Email {string} eingelogged hat")
    public void userHatSichEingelogged(String benutzername, String passwort, String email) {
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
            BenutzerId benutzerId = benutzerRegistrierenUsecase.registriereBenutzer(new BenutzerRegistrierenUsecase.BenutzerRegistrierenCommand(new Benutzer.Name(benutzer.get("Name")), new Benutzer.Passwort(benutzer.get("Passwort")), new Benutzer.Email(benutzer.get("Email")), tenantId));
            log.info("Benutzer {} registriert, ID: {}", benutzer.get("Name"), benutzerId);
            assertThat(benutzerId).isNotNull();
            benutzerToIdMap.put(benutzer.get("Name"), benutzerId);
        });
    }

    @Wenn("der Benutzer {string} den Lied {string} zu einer Playlist {string} hinzufügt")
    public void derBenutzerAliceDenSongFirestarterZuEinerPlaylistFavoritenHinzufügt() {
    }

    @Dann("enthält die Playlist {string} von {string} die Songs:")
    public void enthältDiePlaylistFavoritenVonAliceDieSongs() {
    }

    @Wenn("der Benutzer {string} (der )sich mit dem Passwort {string} und der Email {string} registriert hat")
    public void derBenutzerAliceSichMitDemPasswortAbcUndDerEmailBlaLocalhostComRegistriertHat(String username, String password, String email) {
        BenutzerId benutzerId = benutzerRegistrierenUsecase.registriereBenutzer(new BenutzerRegistrierenUsecase.BenutzerRegistrierenCommand(new Benutzer.Name(username), new Benutzer.Passwort(password), new Benutzer.Email(email), tenantId));
        benutzerToIdMap.put(username, benutzerId);
    }

    @Dann("kennt der Service {int} Lied(er)")
    public void enthältDieDatenbankLied(int c) {
        assertThat(liedAdministrationUsecase.zähleLieder(tenantId)).isEqualTo(c);
    }

    @Dann("kennt der Service {int} Benutzer")
    public void kenntDerServiceBenutzer(int anzahl) {
        assertThat(benutzerAdministrationUsecase.zähleBenutzer(tenantId)).isEqualTo(anzahl);
    }

    @Wenn("der Benutzer {string} das Lied {string} zur Playlist {string} hinzufügt")
    public void derBenutzerAliceDasLiedFirestarterZurPlaylistFavoritenHinzufügt(String benutzername, String liedname, String playlistname) {
        liedZuPlaylistHinzufügenUseCase.liedZuPlaylistHinzufügen(benutzerToIdMap.get(benutzername), titelToIdMap.get(liedname), playlistToIdMap.get(playlistname), tenantId);
    }

    @Dann("enthält die Playlist {string} von {string} {int} Lieder")
    public void enthältDiePlaylistFavoritenVonAliceLieder(String playlist, String benutzer, int anzahl) {
        assertThat(liederInPlaylistAuflistenUseCase.liederInPlaylistAuflisten(benutzerToIdMap.get(benutzer), new Playlist.Name(playlist), tenantId)).hasSize(anzahl);
    }

    @Wenn("der Benutzer {string} die Playlist {string} erstellt")
    public void derBenutzerAliceDiePlaylistFavoritenErstellt(String benutzer, String playlistName) {
        PlaylistId playlistId = playlistAnlegenUsecase.playlistAnlegen(benutzerToIdMap.get(benutzer), new Playlist.Name(playlistName), tenantId);
        log.info("Playlist {} erstellt, ID: {}", playlistName, playlistId);
        playlistToIdMap.put(playlistName, playlistId);
    }

    @Wenn("der Benutzer {string} das Lied {string} abspielt")
    public void derBenutzerAliceDasLiedEpicSongAbspielt(String benutzer, String lied) throws IOException {
        InputStream inputStream = liedAbspielenUsecase.liedStreamen(titelToIdMap.get(lied), tenantId);
        lastReadSongSize = inputStream.readAllBytes().length;
    }

    @Dann("erhält der Benutzer den Song {string} mit mehr als {long} Byte Größe")
    public void erhältDerBenutzerDenSongEpicSongMitMehrAlsMegabyteGröße(String titel, long size) {
        assertThat(lastReadSongSize).isGreaterThan(size);
    }

    @Dann("erhält der Benutzer {string} die Auszeichnung {string}")
    @Und("der Benutzer {string} erhält die Auszeichnung {string}")
    public void erhältDerBenutzerAliceDieAuszeichnungTopUploader(String benutzer, String auszeichnung) {
        BenutzerId benutzerId = benutzerToIdMap.get(benutzer);
        Benutzer benutzerEntity = benutzerAdministrationUsecase.leseBenutzer(benutzerId, tenantId);
        assertThat(benutzerEntity.getAuszeichnungen()).contains(Auszeichnung.valueOf(auszeichnung));
    }

    @Dann("erhält das Lied {string} die Auszeichnung {string}")
    public void erhältDasLiedEpicSongDieAuszeichnungTopSong(String titel, String auszeichnung) {
        LiedId liedId = titelToIdMap.get(titel);
        Lied lied = liedAdministrationUsecase.leseLied(liedId, tenantId);
        assertThat(lied.getAuszeichnungen()).contains(LiedAuszeichnung.valueOf(auszeichnung));
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
