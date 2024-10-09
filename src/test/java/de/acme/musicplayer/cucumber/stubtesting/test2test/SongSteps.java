package de.acme.musicplayer.cucumber.stubtesting.test2test;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.usecases.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Gegebenseien;
import io.cucumber.java.de.Und;
import io.cucumber.java.de.Wenn;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SongSteps {

    private final Map<String, Lied.LiedId> titelToIdMap = new HashMap<>();
    @Autowired
    private BenutzerRegistrierenUsecase benutzerRegistrierenUsecase;
    @Autowired
    private BenutzerAdministrationUsecase benutzerAdministrationUsecase;
    @Autowired
    private LiedAdministrationUsecase liedAdministrationUsecase;
    @Autowired
    private LiedHochladenUseCase liedHochladenUseCase;
    @Autowired
    private LiedZuPlaylistHinzufügenUseCase liedZuPlaylistHinzufügenUseCase;
    @Autowired
    private LiederInPlaylistAuflistenUsecase liederInPlaylistAuflistenUseCase;

    @Gegebenseien("folgende Songs:")
    public void folgendeSongs(DataTable dataTable) {
        dataTable.asMaps()
                .forEach(song ->
                        {
                            String titel = song.get("Titel");
                            Lied.LiedId id = liedHochladenUseCase.liedHochladen(titel, song.get("Interpret"), song.get("Album"), song.get("Genre"), song.get("Erscheinungsjahr"), URI.create(song.get("URI")));
                            titelToIdMap.put(titel, id);
                        }
                );

    }

    @Und("folgende Benutzer:")
    public void folgendeBenutzer(DataTable dataTable) {
        dataTable.asMaps()
                .forEach(benutzer ->
                        benutzerRegistrierenUsecase.benutzerAnmelden(new BenutzerRegistrierenUsecase.BenutzerAnmeldenCommand(new Benutzer.Name(benutzer.get("Name")), new Benutzer.Passwort(benutzer.get("Passwort")), new Benutzer.Email(benutzer.get("Email"))))
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
        benutzerRegistrierenUsecase.benutzerAnmelden(new BenutzerRegistrierenUsecase.BenutzerAnmeldenCommand(new Benutzer.Name(username), new Benutzer.Passwort(password), new Benutzer.Email(email)));
    }

    @Gegebenseien("leere Datenbanken")
    public void leereDatenbanken() {

    }

    @Und("der Benutzer {string} den Lied {string} von {string} hinzufügt")
    public void derBenutzerAliceDenSongFirestarterVonProdigyHinzufügt(String username, String songname, String artist) {

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
        liedZuPlaylistHinzufügenUseCase.liedHinzufügen(benutzername, titelToIdMap.get(liedname), playlistname);

    }

    @Dann("enthält die Playlist {string} von {string} {int} Lieder")
    public void enthältDiePlaylistFavoritenVonAliceLieder(String playlist, String benutzer, int anzahl) {
        assertThat(liederInPlaylistAuflistenUseCase.liederAuflisten(benutzer, playlist)).hasSize(anzahl);
    }
}
