package de.acme.musicplayer.cucumber.real2real;

import com.microsoft.playwright.Page;
import de.acme.musicplayer.applications.users.usecases.BenutzerRegistrierenUsecase;
import de.acme.musicplayer.common.BenutzerId;
import io.cucumber.spring.ScenarioScope;
import lombok.Setter;
import org.springframework.stereotype.Component;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Component
@ScenarioScope
public class PlaywrightUsecases implements BenutzerRegistrierenUsecase {

    private final BrowserContextComponent browserContextComponent;
    @Setter
    private int port;

    @Setter
    private String tenantId;

    public PlaywrightUsecases(BrowserContextComponent browserContextComponent) {
        this.browserContextComponent = browserContextComponent;
    }

    @Override
    public BenutzerId registriereBenutzer(BenutzerRegistrierenCommand benutzerRegistrierenCommand) {
        try (Page page = browserContextComponent.getBrowserContext().browser().newPage()) {
            page.navigate(String.format("http://localhost:%s/?tenantId=%s", port, tenantId));
            assertThat(page).hasTitle("ACME Music Player");
            page.click("#open-register-modal-button");
            page.getByLabel("Username").fill(benutzerRegistrierenCommand.name().benutzername);
            page.getByLabel("Email address").fill(benutzerRegistrierenCommand.email().email);
            page.getByLabel("Password").fill(benutzerRegistrierenCommand.passwort().passwort);
            page.click("#registration-form-submit");
            page.waitForSelector("[data-testid=\"registration-successful-toast\"]");
            String id = page.context().cookies().stream().filter(cookie -> cookie.name.equals("userId")).findFirst().orElseThrow().value;
            return new BenutzerId(id);
        }
    }
}
