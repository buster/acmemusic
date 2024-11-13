package de.acme.support;

public @interface AppModule {
    String name();

    String[] allowedDependencies() default {};

    String[] exposedPackages() default {};
}
