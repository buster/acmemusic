package de.acme.musicplayer.applications.scoreboard.adapters.jdbc.userscoreboard;

import de.acme.musicplayer.applications.scoreboard.ports.UserScoreBoardRepository;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;

public class UserScoreBoardRepositoryStub implements UserScoreBoardRepository {
    @Override
    public void zähleNeuesLied(Benutzer.Id benutzerId) {

    }
}
