package de.acme.musicplayer.componenttests.scoreboard.test2real;

import org.junit.platform.suite.api.*;

import static io.cucumber.core.options.Constants.FILTER_TAGS_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@SelectClasspathResource("features/scoreboard")
@IncludeEngines("cucumber")
@SelectPackages("de.acme")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "de.acme.musicplayer.componenttests.scoreboard.test2real")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@T2R")
@SuppressWarnings("java:S2187")
public class CucumberT2RTest {
}
