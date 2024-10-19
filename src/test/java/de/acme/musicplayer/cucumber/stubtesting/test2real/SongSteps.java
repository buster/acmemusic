package de.acme.musicplayer.cucumber.stubtesting.test2real;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.usecases.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.de.*;
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
    private UUID scenarioUuid;

    @Before
    public void generateUUIDforScenario() {
        scenarioUuid = UUID.randomUUID();
    }

    @After
    public void clearTestData() {
        playlistToIdMap.values().parallelStream()
                .forEach(id -> playlistAdministrationUsecase.löschePlaylist(id));
        titelToIdMap.values().parallelStream()
                .forEach(id -> liedAdministrationUsecase.löscheLied(id));
        benutzerToIdMap.values().parallelStream()
                .forEach(id -> benutzerAdministrationUsecase.löscheBenutzer(id));
    }

    private String replaceUUID(String string) {
        return string.replace("{UUID}", scenarioUuid.toString());
    }

    @Gegebensei("eine leere Datenbank")
    public void gegebenSeiEineLeereDatenbank() {
        benutzerAdministrationUsecase.löscheDatenbank();
        liedAdministrationUsecase.löscheDatenbank();
        playlistAdministrationUsecase.löscheDatenbank();
    }

    @Gegebenseien("folgende Songs:")
    public void folgendeSongs(DataTable dataTable) throws URISyntaxException, IOException {
        for (Map<String, String> song : dataTable.asMaps()) {
            String titel = replaceUUID(song.get("Titel"));
            String dateiname = replaceUUID(song.get("Dateiname"));
            try (InputStream inputStream = new FileInputStream(new File(ClassLoader.getSystemResource(dateiname).toURI()))) {
                Lied.Id id = liedHochladenUseCase.liedHochladen(new Lied.Titel(titel), inputStream);
                titelToIdMap.put(titel, id);
            }
        }
    }

    @Und("folgende Benutzer:")
    public void folgendeBenutzer(DataTable dataTable) {
        dataTable.asMaps().forEach(benutzer -> {
            String name = replaceUUID(benutzer.get("Name"));
            String password = replaceUUID(benutzer.get("Passwort"));
            String email = replaceUUID(benutzer.get("Email"));
            Benutzer.Id id = benutzerRegistrierenUsecase.registriereBenutzer(new BenutzerRegistrierenUsecase.BenutzerRegistrierenCommand(new Benutzer.Name(name), new Benutzer.Passwort(password), new Benutzer.Email(email)));
            benutzerToIdMap.put(name, id);
        });
    }

    @Wenn("der Benutzer {string} (der )sich mit dem Passwort {string} und der Email {string} registriert hat")
    public void derBenutzerAliceSichMitDemPasswortAbcUndDerEmailBlaLocalhostComRegistriertHat(String username, String password, String email) {
        String benutzername = replaceUUID(username);
        String passwort = replaceUUID(password);
        String email1 = replaceUUID(email);
        Benutzer.Id id = benutzerRegistrierenUsecase.registriereBenutzer(new BenutzerRegistrierenUsecase.BenutzerRegistrierenCommand(new Benutzer.Name(benutzername), new Benutzer.Passwort(passwort), new Benutzer.Email(email1)));
        benutzerToIdMap.put(benutzername, id);
    }

    @Dann("kennt der Service {int} Lied(er)")
    public void enthältDieDatenbankLied(int c) {
        assertThat(liedAdministrationUsecase.zähleLieder()).isEqualTo(c);
    }

    @Dann("kennt der Service {int} Benutzer")
    public void kenntDerServiceBenutzer(int anzahl) {
        assertThat(benutzerAdministrationUsecase.zähleBenutzer()).isEqualTo(anzahl);
    }

    @Wenn("der Benutzer {string} das Lied {string} zur Playlist {string} hinzufügt")
    public void derBenutzerAliceDasLiedFirestarterZurPlaylistFavoritenHinzufügt(String benutzername, String liedname, String playlistname) {
        String benutzername1 = replaceUUID(benutzername);
        String liedname1 = replaceUUID(liedname);
        String playlistname1 = replaceUUID(playlistname);
        liedZuPlaylistHinzufügenUseCase.liedHinzufügen(benutzerToIdMap.get(benutzername1), titelToIdMap.get(liedname1), playlistToIdMap.get(playlistname1));
    }

    @Dann("enthält die Playlist {string} von {string} {int} Lieder")
    public void enthältDiePlaylistFavoritenVonAliceLieder(String playlist, String benutzer, int anzahl) {
        String benutzer1 = replaceUUID(benutzer);
        String playlist1 = replaceUUID(playlist);
        assertThat(liederInPlaylistAuflistenUseCase.liederAuflisten(benutzerToIdMap.get(benutzer1), new Playlist.Name(playlist1))).hasSize(anzahl);
    }

    @Wenn("der Benutzer {string} die Playlist {string} erstellt")
    public void derBenutzerAliceDiePlaylistFavoritenErstellt(String benutzer, String playlistName) {
        String benutzer1 = replaceUUID(benutzer);
        String playlistName1 = replaceUUID(playlistName);
        Playlist.Id id = playlistAnlegenUsecase.playlistAnlegen(benutzerToIdMap.get(benutzer1), new Playlist.Name(playlistName1));
        playlistToIdMap.put(playlistName1, id);
    }

    @Wenn("der Benutzer {string} das Lied {string} abspielt")
    public void derBenutzerAliceDasLiedEpicSongAbspielt(String benutzer, String lied) throws IOException {
        String benutzer1 = replaceUUID(benutzer);
        String lied1 = replaceUUID(lied);
        InputStream inputStream = liedAbspielenUsecase.liedStreamen(benutzerToIdMap.get(benutzer1), titelToIdMap.get(lied1));
        lastReadSongSize = inputStream.readAllBytes().length;
    }

    @Dann("erhält der Benutzer den Song {string} mit mehr als {long} Byte Größe")
    public void erhältDerBenutzerDenSongEpicSongMitMehrAlsMegabyteGröße(String titel, long size) {
        assertThat(lastReadSongSize).isGreaterThan(size);
    }
}
