package de.acme.musicplayer.cucumber.stubtesting.test2real;

import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.domain.model.Playlist;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import de.acme.musicplayer.applications.musicplayer.usecases.*;
import de.acme.musicplayer.applications.users.usecases.BenutzerAdministrationUsecase;
import de.acme.musicplayer.applications.users.usecases.BenutzerRegistrierenUsecase;
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

    private final Map<String, Lied.Id> titelToIdMap = new HashMap<>();
    private final Map<String, Benutzer.Id> benutzerToIdMap = new HashMap<>();
    private final Map<String, Playlist.Id> playlistToIdMap = new HashMap<>();
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
        playlistAdministrationUsecase.löscheDatenbank(tenantId);
        liedAdministrationUsecase.löscheDatenbank(tenantId);
        benutzerAdministrationUsecase.löscheDatenbank(tenantId);
        MDC.remove("tenantId");
    }

    @Wenn("sich der Benutzer {string} mit dem Passwort {string} und der Email {string} eingelogged hat")
    public void userHatSichEingelogged(String benutzername, String passwort, String email) {
    }

    @Gegebenseien("folgende Songs:")
    public void folgendeSongs(DataTable dataTable) throws URISyntaxException, IOException {
        for (Map<String, String> song : dataTable.asMaps()) {
            String titel = song.get("Titel");
            try (InputStream inputStream = new FileInputStream(new File(ClassLoader.getSystemResource(song.get("Dateiname")).toURI()))) {
                Lied.Id id = liedHochladenUseCase.liedHochladen(new Lied.Titel(titel), inputStream, tenantId);
                log.info("Song {} hochgeladen, ID: {}", titel, id);
                assertThat(id).isNotNull();
                titelToIdMap.put(titel, id);
            }
        }
    }

    @Und("folgende Benutzer:")
    public void folgendeBenutzer(DataTable dataTable) {
        dataTable.asMaps().forEach(benutzer -> {
            Benutzer.Id id = benutzerRegistrierenUsecase.registriereBenutzer(new BenutzerRegistrierenUsecase.BenutzerRegistrierenCommand(new Benutzer.Name(benutzer.get("Name")), new Benutzer.Passwort(benutzer.get("Passwort")), new Benutzer.Email(benutzer.get("Email")), tenantId));
            log.info("Benutzer {} registriert, ID: {}", benutzer.get("Name"), id);
            assertThat(id).isNotNull();
            benutzerToIdMap.put(benutzer.get("Name"), id);
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
        Benutzer.Id id = benutzerRegistrierenUsecase.registriereBenutzer(new BenutzerRegistrierenUsecase.BenutzerRegistrierenCommand(new Benutzer.Name(username), new Benutzer.Passwort(password), new Benutzer.Email(email), tenantId));
        benutzerToIdMap.put(username, id);
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
        Playlist.Id id = playlistAnlegenUsecase.playlistAnlegen(benutzerToIdMap.get(benutzer), new Playlist.Name(playlistName), tenantId);
        log.info("Playlist {} erstellt, ID: {}", playlistName, id);
        playlistToIdMap.put(playlistName, id);
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
}
