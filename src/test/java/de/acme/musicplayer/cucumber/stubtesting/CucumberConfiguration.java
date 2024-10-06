package de.acme.musicplayer.cucumber.stubtesting;

import de.acme.musicplayer.application.domain.PlaySongService;
import de.acme.musicplayer.application.usecases.PlaySongUseCase;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@CucumberContextConfiguration
@SpringBootTest(classes = StubConfiguration.class)
public class CucumberConfiguration {

}
