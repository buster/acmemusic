package de.acme.e2e;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.Cookie;
import com.microsoft.playwright.options.FilePayload;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Component
public class E2ESongSupport {

    private final Page page;
    private final BrowserContext context;
    private static Playwright playwright;
    private static Browser browser;

    private static String baseUrl;

    private static String browserName;
    private static boolean headless;

    public E2ESongSupport() {
        loadConfig();
        playwright = Playwright.create();
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(headless);
        browser = switch (browserName.toLowerCase()) {
            case "chromium", "chrome" -> playwright.chromium().launch(options);
            case "webkit" -> playwright.webkit().launch(options);
            default -> playwright.firefox().launch(options);
        };
        this.context = browser.newContext(new Browser.NewContextOptions());
        this.page = this.context.newPage();
        this.context.setDefaultTimeout(1000);
        this.page.setDefaultTimeout(1000);
    }

    private static void loadConfig() {
        baseUrl = System.getProperty("e2e.baseUrl",
                System.getenv().getOrDefault("E2E_BASE_URL", "http://localhost:8081"));
        browserName = System.getProperty("e2e.browser",
                System.getenv().getOrDefault("E2E_BROWSER", "firefox"));
        headless = Boolean.parseBoolean(System.getProperty("e2e.headless",
                System.getenv().getOrDefault("E2E_HEADLESS", "true")));
        // Allow CI to pre-install browsers if desired
        System.setProperty("playwright.cli.install",
                System.getProperty("playwright.cli.install", "false"));
    }

    public String registriereBenutzer(String username, String password, String email) {
        page.navigate(baseUrl);

        // Warten, bis die Seite geladen ist
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("ACME Music Player"))).isVisible();

        // Registrierung
        var registrierungLink = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Register"));
        registrierungLink.waitFor();
        registrierungLink.click();

        Locator registerDialog = page.getByRole(AriaRole.DIALOG, new Page.GetByRoleOptions().setName("Benutzerregistrierung"));
        registerDialog.waitFor();

        registerDialog.getByRole(AriaRole.TEXTBOX, new Locator.GetByRoleOptions().setName("Username")).fill(username);
        registerDialog.getByRole(AriaRole.TEXTBOX, new Locator.GetByRoleOptions().setName("Email address")).fill(email);
        registerDialog.getByRole(AriaRole.TEXTBOX, new Locator.GetByRoleOptions().setName("Password")).fill(password);
        registerDialog.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Register")).click();

        assertThat(page.getByTestId("registration-successful-toast")).isVisible();
        return context.cookies().stream()
                .filter(cookie -> cookie.name.equals("userId"))
                .findFirst()
                .orElseThrow()
                .value;
    }

    public String liedHochladen(String filename) throws IOException {
        page.navigate(baseUrl);
        page.click("#nav-link-upload");
        page.getByLabel("Titel").fill(filename);
        Path path = Paths.get("../services/acme/src/test/resources/files/" + filename);
        byte[] mp3Bytes = java.nio.file.Files.readAllBytes(path);
        FileChooser fileChooser = page.waitForFileChooser(() -> page.click("#mp3upload"));
        fileChooser.setFiles(new FilePayload(filename, "audio/mpeg", mp3Bytes));
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