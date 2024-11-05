package de.acme.musicplayer.componenttests.real2real;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ScenarioScope
public class BrowserComponent {

    final Playwright playwright;
    private final Browser browser;

    public BrowserComponent() {
        log.info("creating browser component");
        playwright = Playwright.create();
        browser = playwright.firefox().launch();
    }

    public Browser getBrowser() {
        return browser;
    }

    @PreDestroy
    public void closeBrowser() {
        log.info("closing browser");
        browser.close();
        playwright.close();
    }
}
