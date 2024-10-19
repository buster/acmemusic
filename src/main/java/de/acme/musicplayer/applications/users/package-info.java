@AppModule(
        name = "Users",
        allowedDependencies = {"Musicplayer", "Events"},
        exposedPackages = {"de.acme.musicplayer.applications.users.usecases",
                "de.acme.musicplayer.applications.users.domain.model"}
)
package de.acme.musicplayer.applications.users;

import de.acme.musicplayer.AppModule;
