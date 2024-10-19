@AppModule(
        name = "Gamification",
        allowedDependencies = {"Musicplayer", "Events"},
        exposedPackages = {"de.acme.musicplayer.applications.gamification.usecases",
                "de.acme.musicplayer.applications.gamification.domain.model"}
)
package de.acme.musicplayer.applications.gamification;

import de.acme.musicplayer.AppModule;
