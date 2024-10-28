package de.acme.musicplayer.cucumber.real2real;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import io.cucumber.spring.ScenarioScope;
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
}
