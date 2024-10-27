package de.acme.musicplayer.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.modules.syntax.ModuleRuleDefinition;
import de.acme.musicplayer.AppModule;
import de.acme.musicplayer.ModuleApi;

import static com.tngtech.archunit.library.modules.syntax.ModuleDependencyScope.consideringOnlyDependenciesInAnyPackage;
import static com.tngtech.archunit.library.modules.syntax.ModuleRuleDefinition.modules;

@AnalyzeClasses(packages = "de.acme.musicplayer", importOptions = ImportOption.DoNotIncludeTests.class)
public class ModuleTest {

    @ArchTest
    public static final ArchRule adapters_should_be_cycle_free = ModuleRuleDefinition.modules()
            .definedByPackages("de.acme.musicplayer.applications.(*).adapters..").should().beFreeOfCycles();

    @ArchTest
    static ArchRule modules_should_only_depend_on_each_other_through_module_API =
            modules()
                    .definedByAnnotation(AppModule.class)
                    .should().onlyDependOnEachOtherThroughClassesThat().areAnnotatedWith(ModuleApi.class);

    @ArchTest
    static ArchRule modules_should_only_depend_on_each_other_through_module_API_2 =
            modules()
                    .definedByAnnotation(AppModule.class)
                    .should().respectTheirAllowedDependenciesDeclaredIn("allowedDependencies",
                            consideringOnlyDependenciesInAnyPackage("de.acme.musicplayer.(*).."))
                    .andShould().onlyDependOnEachOtherThroughPackagesDeclaredIn("exposedPackages");
}
