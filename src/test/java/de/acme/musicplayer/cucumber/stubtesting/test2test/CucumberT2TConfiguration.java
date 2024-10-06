package de.acme.musicplayer.cucumber.stubtesting.test2test;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@CucumberContextConfiguration
@SpringBootTest(classes = T2TConfiguration.class)
@DirtiesContext
public class CucumberT2TConfiguration {

}
