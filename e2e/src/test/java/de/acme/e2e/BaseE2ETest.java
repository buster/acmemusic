package de.acme.e2e;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("e2e")
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
    }
}