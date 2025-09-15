package de.acme.e2e.steps;

import de.acme.e2e.E2ESongSupport;
import io.cucumber.java.de.*;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BenutzerRegistrierungSteps {

    private final Map<String, String> benutzerToIdMap = new HashMap<>();
    private final E2ESongSupport support;
    private String lastSongId;

    public BenutzerRegistrierungSteps(E2ESongSupport support) {
        this.support = support;
    }

    @Gegebenseien("folgende Benutzer:")
    public void folgendeBenutzer(io.cucumber.datatable.DataTable dataTable) {
        dataTable.asMaps().forEach(benutzer -> {
            derBenutzerRegistriertSich(benutzer.get("Name"), benutzer.get("Password"), benutzer.get("Email"));
        });
    }

    @Wenn("der Benutzer {string} sich mit dem Passwort {string} und der Email {string} registriert und angemeldet hat")
    public void derBenutzerRegistriertSich(String username, String password, String email) {
        String userId = support.registriereBenutzer(username, password, email);
        Assertions.assertNotNull(userId);
        benutzerToIdMap.put(username, userId);
    }

    @Und("der Benutzer die MP3-Datei {string} hochlädt")
    public void derBenutzerDieMPHochladt(String filename) throws IOException {
        lastSongId = support.liedHochladen(filename);
    }

    @Wenn("der Benutzer {string} die MP3-Datei {string} hochlädt")
    public void derBenutzerDieMPHochladtMitName(String benutzername, String filename) throws IOException {
        String userId = benutzerToIdMap.get(benutzername);
        assertThat(userId).isNotNull();
        support.setzeBenutzerKontext(userId);
        support.liedHochladen(filename);
    }


    @Dann("kennt der Service {int} Benutzer")
    public void kenntDerServiceBenutzer(int count) {
        assertThat(support.zähleBenutzer()).isEqualTo(count);
    }

    @Dann("sollte der Benutzer ein Popup mit dem Inhalt {string} sehen")
    public void sollteBenutzerPopupÜberAuszeichnungsGewinnErhalten(String auszeichnung) {
        String dialogText = support.dialogPopupWirdAngezeigtUndGeschlossen();
        assertThat(dialogText).contains(auszeichnung);
    }

    @Angenommen("eine leere Datenbank")
    public void eineLeereDatenbank() {
        support.löscheBenutzerEvents();
        support.löscheLiedEvents();
        support.löscheScoreboardEvents();
        support.löscheLiedDatenbank();
        support.löscheScoreboardDatenbank();
        support.löscheBenutzerDatenbank();
    }

    @Und("der Benutzer das letzte hochgeladene Lied erfolgreich abspielt")
    public void derBenutzerDasLetzteHochgeladeneLiedAbspielt() {
        support.liedStreamen(lastSongId);
    }
}
