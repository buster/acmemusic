package de.acme.musicplayer.archunit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.base.DescribedPredicate.describe;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packages = "de.acme.musicplayer.applications.users")
public class UsersTest {

    @ArchTest
    public static final ArchRule onion_architecture_in_module = onionArchitecture()
            .adapter("benutzerrepository", "de.acme.musicplayer.applications.users.adapters.jdbc.benutzer..")
            .domainModels("de.acme.musicplayer.applications.users.domain.model..")
            .domainServices("de.acme.musicplayer.applications.users.domain..",
                    "de.acme.musicplayer.applications.users.usecases..",
                    "de.acme.musicplayer.applications.users.ports..")
            .withOptionalLayers(true)
            .ensureAllClassesAreContainedInArchitectureIgnoring(describe("Package Info",
                    input -> input.getSimpleName().endsWith("package-info")));
}
