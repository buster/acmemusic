package de.acme.musicplayer.archunit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.base.DescribedPredicate.describe;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packages = "de.acme.musicplayer.applications.gamification")
public class GamificationTest {

    @ArchTest
    public static final ArchRule onion_architecture_in_module = onionArchitecture()
            .adapter("liedrepository", "de.acme.musicplayer.applications.gamification.adapters.jdbc.lied..")
            .adapter("playlistrepository", "de.acme.musicplayer.applications.gamification.adapters.jdbc.playlist..")
            .adapter("webcontroller", "de.acme.musicplayer.applications.gamification.adapters.web..")
            .adapter("events", "de.acme.musicplayer.applications.gamification.adapters.events..")
            .domainModels("de.acme.musicplayer.applications.gamification.domain.model..")
            .domainServices("de.acme.musicplayer.applications.gamification.domain..",
                    "de.acme.musicplayer.applications.gamification.usecases..",
                    "de.acme.musicplayer.applications.gamification.ports..")
            .withOptionalLayers(true)
            .ensureAllClassesAreContainedInArchitectureIgnoring(describe("Package Info",
                    input -> input.getSimpleName().endsWith("package-info")));


}
