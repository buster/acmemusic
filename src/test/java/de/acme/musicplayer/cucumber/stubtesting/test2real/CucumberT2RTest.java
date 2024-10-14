package de.acme.musicplayer.cucumber.stubtesting.test2real;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.*;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("de.acme")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "de.acme.musicplayer.cucumber.stubtesting.test2real")
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "src/test/resources/features")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@T2R")
public class CucumberT2RTest {
}
