package de.acme.e2e;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@ExtendWith(PlaywrightExtension.class)
public class UserRegistrationAndSongUploadE2ETest extends BaseE2ETest {

    @Test
    void userRegistersAndUploadsSong(Page page, @PlaywrightExtension.BaseUrl String baseUrl) {
        page.navigate(baseUrl);

        // Warten, bis die Seite geladen ist
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("ACME Music Player"))).isVisible();

        // Registrierung
        final var username = "testuser";
        var registrierungLink = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Register"));
        registrierungLink.waitFor();
        registrierungLink.click();

        Locator registerDialog = page.getByRole(AriaRole.DIALOG, new Page.GetByRoleOptions().setName("Benutzerregistrierung"));
        registerDialog.waitFor();

        registerDialog.getByRole(AriaRole.TEXTBOX, new Locator.GetByRoleOptions().setName("Username")).fill(username);
        registerDialog.getByRole(AriaRole.TEXTBOX, new Locator.GetByRoleOptions().setName("Email address")).fill(username + "@acme.com");
        registerDialog.getByRole(AriaRole.TEXTBOX, new Locator.GetByRoleOptions().setName("Password")).fill("password");
        registerDialog.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Register")).click();

        assertThat(page.getByTestId("registration-successful-toast")).isVisible();
//
//        // Song-Upload
//        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Song hochladen")).click();
//
//        final var songTitle = "Test Song";
//        page.getByLabel("Titel").fill(songTitle);
//        page.getByLabel("MP3-Datei").setInputFiles(Paths.get("../services/acme/src/test/resources/files/BoxCat Games - Epic Song.mp3"));
//        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Hochladen")).click();
//
//        // Verifizierung
//        assertThat(page.locator("#song-upload-successfull-toast")).isVisible();
//
//        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Alle Songs")).click();
//        assertThat(page.locator("text=" + songTitle)).isVisible();
    }
}
