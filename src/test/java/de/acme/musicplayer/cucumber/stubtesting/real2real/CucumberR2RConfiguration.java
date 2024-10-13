package de.acme.musicplayer.cucumber.stubtesting.real2real;

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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberR2RConfiguration {


    @TestConfiguration
    public static class T2RConfiguration {

        // BEGIN: Adapter
        @Bean
        public PlaylistPort playlistPort(DSLContext dslContext) {
            return new PlaylistRepository(dslContext);
        }

        @Bean
        public BenutzerPort benutzerPort(DSLContext dslContext) {
            return new BenutzerRepository(dslContext);
        }

        @Bean
        public LiedPort liedPort(DSLContext dslContext) {
            return new LiedRepository(dslContext);
        }

        // END: Adapter

    }
}
