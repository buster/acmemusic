package de.acme.musicplayer;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.springframework.aot.hint.BindingReflectionHintsRegistrar;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class JooqRuntimeHintsRegistrar implements RuntimeHintsRegistrar {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(JooqRuntimeHintsRegistrar.class);

    // siehe https://github.com/jOOQ/jOOQ/issues/8779#issuecomment-2337573460

    private final BindingReflectionHintsRegistrar bindingReflectionHintsRegistrar = new BindingReflectionHintsRegistrar();
    List<String> packagesToScan = List.of(
            "de.acme"
            // Add any other packages where generated classes need to be registered
    );

    @Override
    public void registerHints(RuntimeHints hint, ClassLoader classLoader) {
        for (String packageName : packagesToScan) {
            registerPackage(hint, packageName);
        }
    }

    private void registerPackage(RuntimeHints hint, String packageName) {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        Set<Class<?>> allTypes = reflections.getSubTypesOf(Object.class);
        log.info("Found " + allTypes.size() + " classes for package " + packageName);
        allTypes.stream().map(it -> it.getName()).sorted().forEach(it -> log.info("Registering " + it));
        allTypes.forEach(type -> {
            // Reusing behavior of @RRegisterReflectionForBinding annotation
            bindingReflectionHintsRegistrar.registerReflectionHints(hint.reflection(), type);
        });
        reflections.getSubTypesOf(Serializable.class).forEach(type -> hint.serialization().registerType(type));
    }
}
