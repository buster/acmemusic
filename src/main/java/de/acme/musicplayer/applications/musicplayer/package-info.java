@AppModule(
        name = "Musicplayer",
        allowedDependencies = {"Users"},
        exposedPackages = {"de.acme.musicplayer.applications.musicplayer.usecases",
                "de.acme.musicplayer.applications.musicplayer.domain.model"}
)
package de.acme.musicplayer.applications.musicplayer;

import de.acme.musicplayer.AppModule;