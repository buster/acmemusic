package de.acme.musicplayer.archunit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.base.DescribedPredicate.describe;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packages = "de.acme.musicplayer.applications.musicplayer")
public class MusicplayerTest {

    @ArchTest
    public static final ArchRule onion_architecture_in_module = onionArchitecture()
            .adapter("liedrepository", "de.acme.musicplayer.applications.musicplayer.adapters.jdbc.lied..")
            .adapter("playlistrepository", "de.acme.musicplayer.applications.musicplayer.adapters.jdbc.playlist..")
            .domainModels("de.acme.musicplayer.applications.musicplayer.domain.model..")
            .domainServices("de.acme.musicplayer.applications.musicplayer.domain..",
                    "de.acme.musicplayer.applications.musicplayer.usecases..",
                    "de.acme.musicplayer.applications.musicplayer.ports..")
            .withOptionalLayers(true)
            .ensureAllClassesAreContainedInArchitectureIgnoring(describe("Package Info",
                    input -> input.getSimpleName().endsWith("package-info")));


}
