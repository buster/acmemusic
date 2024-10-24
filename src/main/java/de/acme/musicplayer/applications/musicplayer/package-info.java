@AppModule(
        name = "Musicplayer",
        allowedDependencies = {"Users", "Scoreboard", "Events"},
        exposedPackages = {"de.acme.musicplayer.applications.musicplayer.usecases",
                "de.acme.musicplayer.applications.musicplayer.domain.model",
                "de.acme.musicplayer.applications.musicplayer.domain.events"}
)
package de.acme.musicplayer.applications.musicplayer;

import de.acme.musicplayer.AppModule;
