package de.acme.musicplayer.applications.musicplayer.real2real;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import org.springframework.stereotype.Component;

@Component
public class BrowserComponent {

    final Playwright playwright;
    private final Browser browser;

    public BrowserComponent() {
        playwright = Playwright.create();
        browser = playwright.firefox().launch();
    }

    public Playwright getPlaywright() {
        return playwright;
    }

    public Browser getBrowser() {
        return browser;
    }
}
