package de.acme.musicplayer.cucumber.real2real;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.JSHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.FilePayload;
import com.microsoft.playwright.options.WaitForSelectorState;
import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedAbspielenUsecase;
import de.acme.musicplayer.applications.musicplayer.usecases.LiedHochladenUsecase;
import de.acme.musicplayer.applications.users.usecases.BenutzerRegistrierenUsecase;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.TenantId;
import io.cucumber.spring.ScenarioScope;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Component
@ScenarioScope
@Slf4j
public class PlaywrightUsecases implements BenutzerRegistrierenUsecase, LiedAbspielenUsecase, LiedHochladenUsecase {

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
        try (Page page = browserContextComponent.getBrowserContext().newPage()) {
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

    @Override
    public InputStream liedStreamen(LiedId liedId, TenantId tenantId) {
        try (Page page = browserContextComponent.getBrowserContext().newPage()) {
            page.navigate(String.format("http://localhost:%s/?tenantId=%s", port, tenantId.value()));
            page.click("#nav-link-songlist");
            ElementHandle elementHandle = page.waitForSelector(String.format("[data-testid=\"songlist-%s\"]", liedId.id()));
            JSHandle duration1 = elementHandle.getProperty("duration");
            double duration = Double.parseDouble(duration1.toString());
            log.debug("Returning fake Inputstream for song with id {}", liedId.id());
            return new ByteArrayInputStream(new byte[(int) duration]);
        }
    }

    @Override
    public LiedId liedHochladen(BenutzerId benutzerId, Lied.Titel title, InputStream byteStream, TenantId tenantId) throws IOException {
        try (Page page = browserContextComponent.getBrowserContext().newPage()) {
            page.navigate(String.format("http://localhost:%s/?tenantId=%s", port, tenantId.value()));
            page.click("#nav-link-upload");
            page.getByLabel("Titel").fill(title.titel());
            FileChooser fileChooser = page.waitForFileChooser(() -> page.click("#mp3upload"));
            fileChooser.setFiles(new FilePayload(title.titel(), "audio/mpeg", byteStream.readAllBytes()));
            page.click("#song-upload-button");
            page.waitForSelector("#song-upload-successfull-toast");
            Page.WaitForSelectorOptions waitForSelectorOptions = new Page.WaitForSelectorOptions();
            waitForSelectorOptions.setState(WaitForSelectorState.HIDDEN);
            page.waitForSelector("#songId", waitForSelectorOptions);
            return new LiedId(page.querySelector("#songId").inputValue());
        }
    }
}
