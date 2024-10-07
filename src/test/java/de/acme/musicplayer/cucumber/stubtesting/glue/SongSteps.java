package de.acme.musicplayer.cucumber.stubtesting.glue;

import de.acme.musicplayer.application.usecases.BenutzerAdministrationUsecase;
import de.acme.musicplayer.application.usecases.BenutzerRegistrierenUsecase;
import de.acme.musicplayer.application.usecases.LiedAdministrationUsecase;
import de.acme.musicplayer.application.usecases.LiedHochladenUseCase;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Gegebenseien;
import io.cucumber.java.de.Und;
import io.cucumber.java.de.Wenn;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SongSteps {

    @Autowired
    private BenutzerRegistrierenUsecase benutzerRegistrierenUsecase;

    @Autowired
    private BenutzerAdministrationUsecase benutzerAdministrationUsecase;

    @Autowired
    private LiedAdministrationUsecase liedAdministrationUsecase;

    @Autowired
    private LiedHochladenUseCase liedHochladenUseCase;


    @Gegebenseien("folgende Songs:")
    public void folgendeSongs(DataTable dataTable) {
        dataTable.asMaps()
                .forEach(song ->
                        liedHochladenUseCase.liedHochladen(song.get("Titel"), song.get("Interpret"), song.get("Album"), song.get("Genre"), song.get("Erscheinungsjahr"), URI.create(song.get("URI"))
                        )
                );

    }

    @Und("folgende Benutzer:")
    public void folgendeBenutzer(DataTable dataTable) {
        dataTable.asMaps()
                .forEach(benutzer ->
                        benutzerRegistrierenUsecase.benutzerAnmelden(benutzer.get("Name"), benutzer.get("Passwort"), benutzer.get("Email"))
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
        benutzerRegistrierenUsecase.benutzerAnmelden(username, password, email);
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
}
