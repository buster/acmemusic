@AppModule(
        name = "Events",
        allowedDependencies = {"Musicplayer", "Scoreboard", "Users"},
        exposedPackages = {"de.acme.musicplayer.common", "de.acme.musicplayer.common.events"}
)
package de.acme.musicplayer.common;

import de.acme.musicplayer.AppModule;
