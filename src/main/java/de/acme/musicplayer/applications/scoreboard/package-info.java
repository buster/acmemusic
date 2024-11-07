@AppModule(
        name = "Scoreboard",
        allowedDependencies = {"Musicplayer", "Events", "Users"},
        exposedPackages = {"de.acme.musicplayer.applications.scoreboard.usecases",
                "de.acme.musicplayer.applications.scoreboard.domain.model"}
)
package de.acme.musicplayer.applications.scoreboard;

import de.acme.musicplayer.AppModule;
