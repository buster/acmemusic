@AppModule(
        name = "Events",
        allowedDependencies = {"Musicplayer", "Gamification", "Users"},
        exposedPackages = {"de.acme.musicplayer.events"}
)
package de.acme.musicplayer.events;

import de.acme.musicplayer.AppModule;
