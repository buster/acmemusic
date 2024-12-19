package de.acme.musicplayer.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import de.acme.musicplayer.components.scoreboard.domain.events.BenutzerIstNeuerTopScorer;
import de.acme.musicplayer.components.users.usecases.BenutzerWurdeNeuerTopScorer;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packages = "de.acme.musicplayer.components.scoreboard", importOptions = ImportOption.DoNotIncludePackageInfos.class)
public class ScoreboardArchTest {

    @ArchTest
    public static final ArchRule onion_architecture_in_module = onionArchitecture()
            .adapter("userscoreboard", "de.acme.musicplayer.components.scoreboard.adapters.jdbc.userscoreboard..")
            .adapter("events", "de.acme.musicplayer.components.scoreboard.adapters.events..")
            .applicationServices("de.acme.musicplayer.components.scoreboard.configuration..")
            .domainModels("de.acme.musicplayer.components.scoreboard.domain.common..")
            .domainServices("de.acme.musicplayer.components.scoreboard.domain..",
                    "de.acme.musicplayer.components.scoreboard.usecases..",
                    "de.acme.musicplayer.components.scoreboard.ports..")
            .withOptionalLayers(true)
            .ignoreDependency(BenutzerWurdeNeuerTopScorer.class, BenutzerIstNeuerTopScorer.class);
}
