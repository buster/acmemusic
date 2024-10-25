package de.acme.musicplayer.archunit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.base.DescribedPredicate.describe;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packages = "de.acme.musicplayer.applications.scoreboard")
public class ScoreboardArchTest {

    @ArchTest
    public static final ArchRule onion_architecture_in_module = onionArchitecture()
            .adapter("userscoreboard", "de.acme.musicplayer.applications.scoreboard.adapters.jdbc.userscoreboard..")
            .adapter("eventpublisher", "de.acme.musicplayer.applications.scoreboard.adapters.events..")
            .adapter("events", "de.acme.musicplayer.events..")
            .domainModels("de.acme.musicplayer.applications.scoreboard.adapters.events..")
            .domainServices("de.acme.musicplayer.applications.scoreboard.domain..",
                    "de.acme.musicplayer.applications.scoreboard.usecases..",
                    "de.acme.musicplayer.applications.scoreboard.ports..")
            .withOptionalLayers(true)
            .ensureAllClassesAreContainedInArchitectureIgnoring(describe("Package Info",
                    input -> input.getSimpleName().endsWith("package-info")));


}