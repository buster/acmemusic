package de.acme.musicplayer.cucumber.stubtesting.glue;

import de.acme.musicplayer.application.ports.LiedLadenPort;
import de.acme.musicplayer.application.usecases.BenutzerRegistrierenUsecase;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Gegebenseien;
import io.cucumber.java.de.Und;
import io.cucumber.java.de.Wenn;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SongSteps {

    @Autowired
    private BenutzerRegistrierenUsecase benutzerRegistrierenUsecase;

    @Autowired
    private LiedLadenPort liedLadenPort;


    @Gegebenseien("folgende Songs:")
    public void folgendeSongs(DataTable dataTable) {

    }

    @Und("folgende Benutzer:")
    public void folgendeBenutzer() {
    }

    @Wenn("der Benutzer {string} den Song {string} zu einer Playlist {string} hinzufügt")
    public void derBenutzerAliceDenSongFirestarterZuEinerPlaylistFavoritenHinzufügt() {
    }

    @Dann("enthält die Playlist {string} von {string} die Songs:")
    public void enthältDiePlaylistFavoritenVonAliceDieSongs() {
    }

    @Wenn("der Benutzer {string} sich mit dem Passwort {string} und der Email {string} registriert hat")
    public void derBenutzerAliceSichMitDemPasswortAbcUndDerEmailBlaLocalhostComRegistriertHat(String username, String password, String email) {
        benutzerRegistrierenUsecase.benutzerAnmelden(username, password, email);
    }

    @Gegebenseien("leere Datenbanken")
    public void leereDatenbanken() {

    }

    @Und("der Benutzer {string} den Song {string} von {string} hinzufügt")
    public void derBenutzerAliceDenSongFirestarterVonProdigyHinzufügt(String username, String songname, String artist) {

    }

    @Dann("enthält die Datenbank {int} Lied")
    public void enthältDieDatenbankLied(int c) {
        assertThat(liedLadenPort.zähleLieder()).isEqualTo(c);
    }
}
