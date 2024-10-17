package de.acme.musicplayer;

public @interface AppModule {
    String name();

    String[] allowedDependencies() default {};

    String[] exposedPackages() default {};
}