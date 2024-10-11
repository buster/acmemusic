package de.acme.musicplayer.cucumber.stubtesting.test2real;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.usecases.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.de.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

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

    @Gegebensei("eine leere Datenbank")
    public void gegebenSeiEineLeereDatenbank() {
        benutzerAdministrationUsecase.löscheDatenbank();
        playlistAdministrationUsecase.löscheDatenbank();
        liedAdministrationUsecase.löscheDatenbank();
    }

    @Gegebenseien("folgende Songs:")
    public void folgendeSongs(DataTable dataTable) throws URISyntaxException, IOException {
        for (Map<String, String> song : dataTable.asMaps()) {
            String titel = song.get("Titel");
            try (InputStream inputStream = new FileInputStream(new File(ClassLoader.getSystemResource(song.get("Dateiname")).toURI()))) {
                Lied.Id id = liedHochladenUseCase.liedHochladen(new Lied.Titel(titel), inputStream);
                titelToIdMap.put(titel, id);
            }
        }
    }

    @Und("folgende Benutzer:")
    public void folgendeBenutzer(DataTable dataTable) {
        dataTable.asMaps()
                .forEach(benutzer ->
                        {
                            Benutzer.Id id = benutzerRegistrierenUsecase.registriereBenutzer(new BenutzerRegistrierenUsecase.BenutzerRegistrierenCommand(new Benutzer.Name(benutzer.get("Name")), new Benutzer.Passwort(benutzer.get("Passwort")), new Benutzer.Email(benutzer.get("Email"))));
                            benutzerToIdMap.put(benutzer.get("Name"), id);
                        }
                );
    }

    @Wenn("der Benutzer {string} den Lied {string} zu einer Playlist {string} hinzufügt")
    public void derBenutzerAliceDenSongFirestarterZuEinerPlaylistFavoritenHinzufügt() {
    }

    @Dann("enthält die Playlist {string} von {string} die Songs:")
    public void enthältDiePlaylistFavoritenVonAliceDieSongs() {
    }

    @Wenn("der Benutzer {string} (der )sich mit dem Passwort {string} und der Email {string} registriert hat")
    public void derBenutzerAliceSichMitDemPasswortAbcUndDerEmailBlaLocalhostComRegistriertHat(String username, String password, String email) {
        Benutzer.Id id = benutzerRegistrierenUsecase.registriereBenutzer(new BenutzerRegistrierenUsecase.BenutzerRegistrierenCommand(new Benutzer.Name(username), new Benutzer.Passwort(password), new Benutzer.Email(email)));
        benutzerToIdMap.put(username, id);
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
        liedZuPlaylistHinzufügenUseCase.liedHinzufügen(benutzerToIdMap.get(benutzername), titelToIdMap.get(liedname), playlistToIdMap.get(playlistname));
    }

    @Dann("enthält die Playlist {string} von {string} {int} Lieder")
    public void enthältDiePlaylistFavoritenVonAliceLieder(String playlist, String benutzer, int anzahl) {
        assertThat(liederInPlaylistAuflistenUseCase.liederAuflisten(benutzerToIdMap.get(benutzer), new Playlist.Name(playlist))).hasSize(anzahl);
    }

    @Wenn("der Benutzer {string} die Playlist {string} erstellt")
    public void derBenutzerAliceDiePlaylistFavoritenErstellt(String benutzer, String playlistName) {
        Playlist.Id id = playlistAnlegenUsecase.playlistAnlegen(benutzerToIdMap.get(benutzer), new Playlist.Name(playlistName));
        playlistToIdMap.put(playlistName, id);
    }
}
