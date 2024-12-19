@AppModule(
        name = "CommonAPI",
        allowedDependencies = {"Musicplayer", "Scoreboard", "Users", "Events"},
        exposedPackages = {"de.acme.musicplayer.common.api"}
)
package de.acme.musicplayer.common.api;

import de.acme.support.AppModule;
