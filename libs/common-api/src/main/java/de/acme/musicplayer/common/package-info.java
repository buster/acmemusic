@AppModule(
        name = "Events",
        allowedDependencies = {"Musicplayer", "Scoreboard", "Users"},
        exposedPackages = {"de.acme.musicplayer.common"}
)
package de.acme.musicplayer.common;

import de.acme.support.AppModule;
