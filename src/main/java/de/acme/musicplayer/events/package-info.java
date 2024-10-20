@AppModule(
        name = "Events",
        allowedDependencies = {"Musicplayer", "Scoreboard", "Users"},
        exposedPackages = {"de.acme.musicplayer.events"}
)
package de.acme.musicplayer.events;

import de.acme.musicplayer.AppModule;
