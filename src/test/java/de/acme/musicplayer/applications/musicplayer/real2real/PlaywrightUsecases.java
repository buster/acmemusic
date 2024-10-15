package de.acme.musicplayer.applications.musicplayer.real2real;

import com.microsoft.playwright.Page;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.applications.users.usecases.BenutzerRegistrierenUsecase;
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
    public Benutzer.Id registriereBenutzer(BenutzerRegistrierenCommand benutzerRegistrierenCommand) {
        try (Page page = browserContextComponent.getBrowserContext().browser().newPage()) {
            page.navigate(String.format("http://localhost:%s/?tenantId=%s", port, tenantId));
            assertThat(page).hasTitle("ACME Music Player");
            page.click("#open-register-modal-button");
            page.getByLabel("Username").fill(benutzerRegistrierenCommand.name().benutzername);
            page.getByLabel("Email address").fill(benutzerRegistrierenCommand.email().email);
            page.getByLabel("Password").fill(benutzerRegistrierenCommand.passwort().passwort);
            page.click("#registration-form-submit");
            String id = page.inputValue("#userId");
            return new Benutzer.Id(id);
        }
    }
}
