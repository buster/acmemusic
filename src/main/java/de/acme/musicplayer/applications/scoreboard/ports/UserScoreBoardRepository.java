package de.acme.musicplayer.applications.scoreboard.ports;

import de.acme.musicplayer.applications.users.domain.model.Benutzer;

public interface UserScoreBoardRepository {
    void zähleNeuesLied(Benutzer.Id benutzerId);
}
