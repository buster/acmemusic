package de.acme.musicplayer.applications.musicplayer.real2real;

import com.microsoft.playwright.BrowserContext;
import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
public class BrowserContextComponent {

    private final BrowserContext browserContext;

    public BrowserContextComponent(BrowserComponent browserComp) {
        browserContext = browserComp.getBrowser().newContext();
    }

    public BrowserContext getBrowserContext() {
        return browserContext;
    }
}
