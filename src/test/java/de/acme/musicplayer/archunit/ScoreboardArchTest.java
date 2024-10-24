package de.acme.musicplayer.archunit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import de.acme.musicplayer.applications.scoreboard.domain.events.BenutzerIstNeuerTopScorer;
import de.acme.musicplayer.applications.users.usecases.AuszeichnungFürNeueTopScorer;

import static com.tngtech.archunit.base.DescribedPredicate.describe;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packages = "de.acme.musicplayer.applications.scoreboard")
public class ScoreboardArchTest {

    @ArchTest
    public static final ArchRule onion_architecture_in_module = onionArchitecture()
            .adapter("userscoreboard", "de.acme.musicplayer.applications.scoreboard.adapters.jdbc.userscoreboard..")
            .adapter("events", "de.acme.musicplayer.applications.scoreboard.adapters.events..")
            .domainModels("de.acme.musicplayer.applications.scoreboard.domain.common..")
            .domainServices("de.acme.musicplayer.applications.scoreboard.domain..",
                    "de.acme.musicplayer.applications.scoreboard.usecases..",
                    "de.acme.musicplayer.applications.scoreboard.ports..")
            .withOptionalLayers(true)
            .ignoreDependency(AuszeichnungFürNeueTopScorer.class, BenutzerIstNeuerTopScorer.class)
            .ensureAllClassesAreContainedInArchitectureIgnoring(describe("Package Info",
                    input -> input.getSimpleName().endsWith("package-info")));


}
