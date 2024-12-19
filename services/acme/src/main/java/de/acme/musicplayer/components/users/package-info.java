@AppModule(
        name = "Users",
        allowedDependencies = {"Musicplayer", "Events", "Scoreboard", "CommonAPI"},
        exposedPackages = {"de.acme.musicplayer.components.users.usecases",
                "de.acme.musicplayer.components.users.domain.events",
        }
)
package de.acme.musicplayer.components.users;

import de.acme.support.AppModule;
