package de.acme.musicplayer.applications.scoreboard.adapters.jdbc.userscoreboard;

import de.acme.musicplayer.applications.scoreboard.ports.UserScoreBoardRepository;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Comparator.comparingInt;

public class UserScoreBoardRepositoryStub implements UserScoreBoardRepository {

    private ConcurrentHashMap<Benutzer.Id, Integer> scoreBoard = new ConcurrentHashMap<>();

    @Override
    public void zähleNeuesLied(Benutzer.Id benutzerId) {
        scoreBoard.put(benutzerId, scoreBoard.getOrDefault(benutzerId, 0) + 1);
    }

    @Override
    public Benutzer.Id höchstePunktZahl() {
        return scoreBoard.entrySet().stream()
                .max(comparingInt(Map.Entry::getValue))
                .map(HashMap.Entry::getKey)
                .orElse(null);
    }
}
