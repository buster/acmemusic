package de.acme.e2e.steps;

import de.acme.e2e.E2ESongSupport;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CucumberTestConfiguration {

    @Bean
    public E2ESongSupport e2ESongSupport() {
        return new E2ESongSupport();
    }
}
