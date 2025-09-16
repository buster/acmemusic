package de.acme.musicplayer.componenttests.scoreboard;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@SelectClasspathResource("features/scoreboard")
@IncludeEngines("cucumber")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "de.acme.musicplayer.componenttests.scoreboard")
@SuppressWarnings("java:S2187")
public class ScoreboardComponentTest {
}
