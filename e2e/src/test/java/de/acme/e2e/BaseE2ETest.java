package de.acme.e2e;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(PlaywrightExtension.class)
class BaseE2ETest {

    @Test
    void playwrightExtensionInjectsAllArtifacts(Page page,
                                               BrowserContext context,
                                               Browser browser,
                                               Playwright playwright,
                                               @PlaywrightExtension.BaseUrl String baseUrl) {
        assertNotNull(page);
        assertNotNull(context);
        assertNotNull(browser);
        assertNotNull(playwright);
        assertNotNull(baseUrl);

        page.navigate(baseUrl);
        assertThat(page).hasTitle("ACME Music Player");
    }
}