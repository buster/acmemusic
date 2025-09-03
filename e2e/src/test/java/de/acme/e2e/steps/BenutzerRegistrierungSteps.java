package de.acme.e2e.steps;

import de.acme.e2e.E2ESongSupport;
import io.cucumber.java.de.Gegebenseien;
import io.cucumber.java.de.Wenn;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.Map;

public class BenutzerRegistrierungSteps {

    private final Map<String, String> benutzerToIdMap = new HashMap<>();
    private final E2ESongSupport support;

    public BenutzerRegistrierungSteps(E2ESongSupport support) {
        this.support = support;
    }

    @Gegebenseien("folgende Benutzer:")
    public void folgendeBenutzer(io.cucumber.datatable.DataTable dataTable) {
        dataTable.asMaps().forEach(benutzer -> {
            String userId = support.registriereBenutzer(
                    benutzer.get("Name"),
                    benutzer.get("Passwort"),
                    benutzer.get("Email")
            );
            Assertions.assertNotNull(userId);
            benutzerToIdMap.put(benutzer.get("Name"), userId);
        });
    }

    @Wenn("der Benutzer {string} sich mit dem Passwort {string} und der Email {string} registriert hat")
    public void derBenutzerRegistriertSich(String username, String password, String email) {
        String userId = support.registriereBenutzer(username, password, email);
        Assertions.assertNotNull(userId);
        benutzerToIdMap.put(username, userId);
    }
}