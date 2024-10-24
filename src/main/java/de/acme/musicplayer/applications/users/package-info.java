@AppModule(
        name = "Users",
        allowedDependencies = {"Musicplayer", "Events", "Scoreboard"},
        exposedPackages = {"de.acme.musicplayer.applications.users.usecases",
                "de.acme.musicplayer.applications.users.domain.events",
        }
)
package de.acme.musicplayer.applications.users;

import de.acme.musicplayer.AppModule;
