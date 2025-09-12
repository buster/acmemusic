package de.acme.e2e.steps;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = CucumberTestConfiguration.class)
public class CucumberE2EContextConfiguration {
}
