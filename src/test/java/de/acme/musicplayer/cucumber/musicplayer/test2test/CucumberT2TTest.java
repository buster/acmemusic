package de.acme.musicplayer.cucumber.musicplayer.test2test;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.FILTER_TAGS_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@SelectClasspathResource("features/musicplayer")
@IncludeEngines("cucumber")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "de.acme.musicplayer.cucumber.musicplayer.test2test")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@T2T")
@SuppressWarnings("java:S2187")
public class CucumberT2TTest {
}
