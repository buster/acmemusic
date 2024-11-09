package de.acme.musicplayer.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packages = "de.acme.musicplayer.components.users", importOptions = ImportOption.DoNotIncludePackageInfos.class)
public class UsersArchTest {

    @ArchTest
    public static final ArchRule onion_architecture_in_module = onionArchitecture()
            .adapter("benutzerrepository", "de.acme.musicplayer.components.users.adapters.jdbc.benutzer..")
            .adapter("controller", "de.acme.musicplayer.components.users.adapters.web..")
            .adapter("events", "de.acme.musicplayer.components.users.adapters.events..")
            .applicationServices("de.acme.musicplayer.components.users.configuration..")
            .domainModels("de.acme.musicplayer.components.users.domain.model..",
                    "de.acme.musicplayer.components.users.domain.events..")
            .domainServices("de.acme.musicplayer.components.users.domain..",
                    "de.acme.musicplayer.components.users.usecases..",
                    "de.acme.musicplayer.components.users.ports..")
            .withOptionalLayers(true);
}
