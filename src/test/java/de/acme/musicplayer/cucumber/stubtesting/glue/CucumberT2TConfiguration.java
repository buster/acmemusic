package de.acme.musicplayer.cucumber.stubtesting.glue;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = T2TConfiguration.class)
public class CucumberT2TConfiguration {

}
