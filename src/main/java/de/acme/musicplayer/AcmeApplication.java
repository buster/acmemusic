package de.acme.musicplayer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@ExcludeFromJacocoGenerated
@SpringBootApplication
@ImportRuntimeHints(JooqRuntimeHintsRegistrar.class)
public class AcmeApplication {

    @ExcludeFromJacocoGenerated
    public static void main(String[] args) {
        SpringApplication.run(AcmeApplication.class, args);
    }

}
