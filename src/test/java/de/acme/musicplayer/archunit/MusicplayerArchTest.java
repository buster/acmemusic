package de.acme.musicplayer.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import de.acme.musicplayer.components.musicplayer.domain.events.NeuesLiedWurdeAngelegt;
import de.acme.musicplayer.components.scoreboard.usecases.ZähleNeueLiederUsecase;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packages = "de.acme.musicplayer.components.musicplayer", importOptions = ImportOption.DoNotIncludePackageInfos.class)
public class MusicplayerArchTest {

    @ArchTest
    public static final ArchRule onion_architecture_in_module = onionArchitecture()
            .adapter("liedrepository", "de.acme.musicplayer.components.musicplayer.adapters.jdbc.lied..")
            .adapter("webcontroller", "de.acme.musicplayer.components.musicplayer.adapters.web..")
            .adapter("eventlistener", "de.acme.musicplayer.components.musicplayer.adapters.events..")
            .applicationServices("de.acme.musicplayer.components.musicplayer.configuration..")
            .domainModels("de.acme.musicplayer.components.musicplayer.domain.model..",
                    "de.acme.musicplayer.components.musicplayer.domain.events..")
            .domainServices("de.acme.musicplayer.components.musicplayer.domain..",
                    "de.acme.musicplayer.components.musicplayer.usecases..",
                    "de.acme.musicplayer.components.musicplayer.ports..")
            .withOptionalLayers(true)
            .ignoreDependency(ZähleNeueLiederUsecase.class, NeuesLiedWurdeAngelegt.class);


}
