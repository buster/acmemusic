package de.acme.musicplayer.archunit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import de.acme.musicplayer.applications.musicplayer.domain.events.NeuesLiedWurdeAngelegt;
import de.acme.musicplayer.applications.scoreboard.usecases.ZähleNeueLiederUsecase;

import static com.tngtech.archunit.base.DescribedPredicate.describe;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packages = "de.acme.musicplayer.applications.musicplayer")
public class MusicplayerArchTest {

    @ArchTest
    public static final ArchRule onion_architecture_in_module = onionArchitecture()
            .adapter("liedrepository", "de.acme.musicplayer.applications.musicplayer.adapters.jdbc.lied..")
            .adapter("playlistrepository", "de.acme.musicplayer.applications.musicplayer.adapters.jdbc.playlist..")
            .adapter("webcontroller", "de.acme.musicplayer.applications.musicplayer.adapters.web..")
            .adapter("eventlistener", "de.acme.musicplayer.applications.musicplayer.adapters.events..")
            .domainModels("de.acme.musicplayer.applications.musicplayer.domain.model..",
                    "de.acme.musicplayer.applications.musicplayer.domain.events..")
            .domainServices("de.acme.musicplayer.applications.musicplayer.domain..",
                    "de.acme.musicplayer.applications.musicplayer.usecases..",
                    "de.acme.musicplayer.applications.musicplayer.ports..")
            .withOptionalLayers(true)
            .ignoreDependency(ZähleNeueLiederUsecase.class, NeuesLiedWurdeAngelegt.class)
            .ensureAllClassesAreContainedInArchitectureIgnoring(describe("Package Info",
                    input -> input.getSimpleName().endsWith("package-info")));


}
