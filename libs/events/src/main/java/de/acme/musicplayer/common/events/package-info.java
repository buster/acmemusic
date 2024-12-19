@AppModule(
        name = "Events",
        allowedDependencies = {"Users", "Scoreboard", "Musicplayer", "CommonAPI"},
        exposedPackages = {"de.acme.musicplayer.common.events"}
)
package de.acme.musicplayer.common.events;

import de.acme.support.AppModule;
