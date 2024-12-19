package de.acme.musicplayer.componenttests.users.test2real;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(properties = "events.publisher.publish-events-to-application=false")
public class CucumberT2RConfiguration {

    // No Beans needed: Ports are configured in Application (no stubbing)
    // Usecases are used directly
}
