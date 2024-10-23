package de.acme.musicplayer.cucumber.stubtesting.test2real;

import de.acme.musicplayer.adapters.jdbc.BenutzerRepository;
import de.acme.musicplayer.adapters.jdbc.LiedRepository;
import de.acme.musicplayer.adapters.jdbc.PlaylistRepository;
import de.acme.musicplayer.application.ports.BenutzerPort;
import de.acme.musicplayer.application.ports.LiedPort;
import de.acme.musicplayer.application.ports.PlaylistPort;
import io.cucumber.spring.CucumberContextConfiguration;
import org.jooq.DSLContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;

@CucumberContextConfiguration
@SpringBootTest
public class CucumberT2RConfiguration {

    // No Beans needed: Ports are configured in Application (no stubbing)
    // Usecases are used directly
}
