package de.acme.musicplayer.cucumber.real2real;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import io.cucumber.spring.ScenarioScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
@Slf4j
public class BrowserContextComponent {

    private final BrowserContext browserContext;
    private final Page page;

    public BrowserContextComponent(BrowserComponent browserComp) {
        log.info("creating browser context component");
        browserContext = browserComp.getBrowser().newContext();
        page = browserContext.newPage();
    }

    public BrowserContext getBrowserContext() {
        log.info("returning browser context");
        return browserContext;
    }

    public Page getCurrentPage() {
        return page;
    }
}
