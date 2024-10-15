package de.acme.musicplayer.applications;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.modules.syntax.ModuleRuleDefinition;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packages = "de.acme.musicplayer", importOptions = ImportOption.DoNotIncludeTests.class)
public class MusicPlayerTest {
//    @ArchTest
//    public static final ArchRule onion = onionArchitecture()
//            .domainModels("de.acme.musicplayer.applications.musicplayer.domain.model..")
//            .domainServices("de.acme.musicplayer.applications.musicplayer.domain..")

    @ArchTest
    public static final ArchRule modules = ModuleRuleDefinition.modules()
            .definedByPackages("de.acme.musicplayer.applications.(*)..").should().beFreeOfCycles();

    @ArchTest
    public static final ArchRule modules2 = ModuleRuleDefinition.modules()
            .definedByPackages("de.acme.musicplayer.applications.(*)..")
            .should()
            .onlyDependOnEachOtherThroughClassesThat().areMetaAnnotatedWith("ModuleApi");
//            .areInterfaces()
//            .andShould()
//            .onlyDependOnEachOtherThroughClassesThat()
//            .haveSimpleNameEndingWith("Usecase");
}
