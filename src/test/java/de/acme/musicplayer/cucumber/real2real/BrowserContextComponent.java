package de.acme.musicplayer.cucumber.real2real;

import com.microsoft.playwright.BrowserContext;
import io.cucumber.spring.ScenarioScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
@Slf4j
public class BrowserContextComponent {

    private final BrowserContext browserContext;

    public BrowserContextComponent(BrowserComponent browserComp) {
        log.info("creating browser context component");
        browserContext = browserComp.getBrowser().newContext();
    }

    public BrowserContext getBrowserContext() {
        log.info("returning browser context");
        return browserContext;
    }
}
