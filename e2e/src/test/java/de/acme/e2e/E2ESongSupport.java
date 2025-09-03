package de.acme.e2e;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.Cookie;
import com.microsoft.playwright.options.FilePayload;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class E2ESongSupport {

    private final Page page;
    private final BrowserContext context;
    private final String baseUrl;

    public E2ESongSupport(Page page, BrowserContext context, String baseUrl) {
        this.page = page;
        this.context = context;
        this.baseUrl = baseUrl;
    }

    public String registriereBenutzer(String username, String password, String email) {
        page.navigate(baseUrl);
        assertThat(page).hasTitle("ACME Music Player");
        page.click("#open-register-modal-button");
        page.getByLabel("Username").fill(username);
        page.getByLabel("Email address").fill(email);
        page.getByLabel("Password").fill(password);
        page.click("#registration-form-submit");
        page.waitForSelector("[data-testid=\"registration-successful-toast\"]");
        return context.cookies().stream()
                .filter(cookie -> cookie.name.equals("userId"))
                .findFirst()
                .orElseThrow()
                .value;
    }

    public String liedHochladen(String userId, String titel, byte[] mp3Bytes) throws IOException {
        page.navigate(baseUrl);
        setUserIdCookie(userId);
        page.click("#nav-link-upload");
        page.getByLabel("Titel").fill(titel);
        FileChooser fileChooser = page.waitForFileChooser(() -> page.click("#mp3upload"));
        fileChooser.setFiles(new FilePayload(titel, "audio/mpeg", mp3Bytes));
        page.click("#song-upload-button");
        page.waitForSelector("#song-upload-successfull-toast");
        Page.WaitForSelectorOptions waitForSelectorOptions = new Page.WaitForSelectorOptions();
        waitForSelectorOptions.setState(WaitForSelectorState.HIDDEN);
        page.waitForSelector("#songId", waitForSelectorOptions);
        return page.querySelector("#songId").inputValue();
    }

    public InputStream liedStreamen(String liedId) {
        page.navigate(baseUrl);
        page.click("#nav-link-songlist");
        ElementHandle elementHandle = page.waitForSelector(String.format("[data-testid=\"songlist-%s\"]", liedId));
        double duration = Double.parseDouble(elementHandle.getProperty("duration").toString());
        return new ByteArrayInputStream(new byte[(int) duration]);
    }

    public long zähleBenutzer() {
        page.click("#nav-link-adminpage");
        String userCount = page.getByTestId("userCount").textContent();
        return Long.parseLong(userCount);
    }

    public String leseBenutzer(String userId) {
        page.click("#nav-link-adminpage");
        page.getByTestId("benutzerId").fill(userId);
        page.click("#read-user");
        assertThat(page.getByTestId("adminpage-return")).containsText("{");
        return page.getByTestId("adminpage-return").textContent();
    }

    public void löscheBenutzerDatenbank() {
        page.click("#nav-link-adminpage");
        page.click("#delete-user-database");
        assertThat(page.getByTestId("adminpage-return")).containsText("Benutzerdatenbank gelöscht");
    }

    public void löscheBenutzerEvents() {
        page.click("#nav-link-adminpage");
        page.click("#delete-user-events");
        assertThat(page.getByTestId("adminpage-return")).containsText("BenutzerEvents gelöscht");
    }

    public void löscheLiedDatenbank() {
        page.click("#nav-link-adminpage");
        page.click("#delete-song-database");
        assertThat(page.getByTestId("adminpage-return")).containsText("Liederdatenbank gelöscht");
    }

    public void löscheLiedEvents() {
        page.click("#nav-link-adminpage");
        page.click("#delete-song-events");
        assertThat(page.getByTestId("adminpage-return")).containsText("LiederEvents gelöscht");
    }

    public void löscheScoreboardDatenbank() {
        page.click("#nav-link-adminpage");
        page.click("#delete-scoreboard-database");
        assertThat(page.getByTestId("adminpage-return")).containsText("Scoreboarddatenbank gelöscht");
    }

    public void löscheScoreboardEvents() {
        page.click("#nav-link-adminpage");
        page.click("#delete-scoreboard-events");
        assertThat(page.getByTestId("adminpage-return")).containsText("ScoreboardEvents gelöscht");
    }

    private void setUserIdCookie(String userId) {
        Cookie cookie = new Cookie("userId", userId);
        cookie.domain = "localhost";
        cookie.path = "/";
        context.addCookies(List.of(cookie));
    }
}