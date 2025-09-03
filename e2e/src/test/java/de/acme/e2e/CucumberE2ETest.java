package de.acme.e2e;

import org.junit.platform.suite.api.*;

import static io.cucumber.core.options.Constants.FILTER_TAGS_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@SelectClasspathResource("features")
@IncludeEngines("cucumber")
@SelectPackages("de.acme")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "de.acme.e2e.steps")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@E2E")
@ConfigurationParameter(key = "cucumber.plugin", value = "pretty")
@SuppressWarnings("java:S2187")
public class CucumberE2ETest {
}