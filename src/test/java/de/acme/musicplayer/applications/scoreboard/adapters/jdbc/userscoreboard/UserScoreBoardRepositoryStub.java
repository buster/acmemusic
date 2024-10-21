package de.acme.musicplayer.applications.scoreboard.adapters.jdbc.userscoreboard;

import de.acme.musicplayer.applications.scoreboard.ports.UserScoreBoardRepository;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;

import java.util.HashMap;

public class UserScoreBoardRepositoryStub implements UserScoreBoardRepository {

    private HashMap<Benutzer.Id, Integer> scoreBoard = new HashMap<>();

    @Override
    public void zähleNeuesLied(Benutzer.Id benutzerId) {
        scoreBoard.put(benutzerId, scoreBoard.getOrDefault(benutzerId, 0) + 1);
    }

    @Override
    public Benutzer.Id höchstePunktZahl() {
        return scoreBoard.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() - entry2.getValue())
                .map(HashMap.Entry::getKey)
                .orElse(null);
    }
}
