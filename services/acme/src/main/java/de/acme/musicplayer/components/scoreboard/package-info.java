@AppModule(
        name = "Scoreboard",
        allowedDependencies = {"Musicplayer", "Events", "Users"},
        exposedPackages = {"de.acme.musicplayer.components.scoreboard.usecases",
                "de.acme.musicplayer.components.scoreboard.domain.events",
        }
)
package de.acme.musicplayer.components.scoreboard;

import de.acme.support.AppModule;
