package de.acme.musicplayer.cucumber.stubtesting;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = StubConfiguration.class)
public class CucumberConfiguration {

}
