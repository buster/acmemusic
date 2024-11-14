@AppModule(
        name = "Musicplayer",
        allowedDependencies = {"Users", "Scoreboard", "Events", "CommonAPI"},
        exposedPackages = {"de.acme.musicplayer.components.musicplayer.usecases",
                "de.acme.musicplayer.components.musicplayer.domain.events"}
)
package de.acme.musicplayer.components.musicplayer;

import de.acme.support.AppModule;
