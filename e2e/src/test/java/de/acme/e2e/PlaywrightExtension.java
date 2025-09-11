package de.acme.e2e;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.extension.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public final class PlaywrightExtension implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback, ParameterResolver {

    private static Playwright playwright;
    private static Browser browser;
    private static String baseUrl;
    private static String browserName;
    private static boolean headless;

    private BrowserContext context;
    private Page page;

    public static String baseUrl() {
        return baseUrl;
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

    @Override
    public void beforeAll(ExtensionContext context) {
        loadConfig();
        playwright = Playwright.create();
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(headless);
        browser = switch (browserName.toLowerCase()) {
            case "chromium", "chrome" -> playwright.chromium().launch(options);
            case "webkit" -> playwright.webkit().launch(options);
            default -> playwright.firefox().launch(options);
        };
    }

    @Override
    public void afterAll(ExtensionContext context) {
       if (browser != null) {
           browser.close();
       }
       if (playwright != null) {
           playwright.close();
       }
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        this.context = browser.newContext(new Browser.NewContextOptions());
        this.page = this.context.newPage();
        this.context.setDefaultTimeout(1000);
        this.page.setDefaultTimeout(1000);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        if (this.context != null) {
            this.context.close();
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        Class<?> type = parameterContext.getParameter().getType();
        if (type.equals(Page.class)) return true;
        if (type.equals(BrowserContext.class)) return true;
        if (type.equals(Browser.class)) return true;
        if (type.equals(Playwright.class)) return true;
        return type.equals(String.class) && parameterContext.isAnnotated(BaseUrl.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> type = parameterContext.getParameter().getType();
        if (type.equals(Page.class)) return page;
        if (type.equals(BrowserContext.class)) return context;
        if (type.equals(Browser.class)) return browser;
        if (type.equals(Playwright.class)) return playwright;
        if (type.equals(String.class) && parameterContext.isAnnotated(BaseUrl.class)) return baseUrl;
        throw new ParameterResolutionException("Unsupported parameter: " + type);
    }

    @Retention(RUNTIME)
    @Target(PARAMETER)
    public @interface BaseUrl {
    }
}