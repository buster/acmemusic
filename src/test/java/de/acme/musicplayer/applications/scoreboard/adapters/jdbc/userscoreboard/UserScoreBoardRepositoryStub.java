package de.acme.musicplayer.applications.scoreboard.adapters.jdbc.userscoreboard;

import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import de.acme.musicplayer.applications.scoreboard.ports.UserScoreBoardRepository;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Comparator.comparingInt;

public class UserScoreBoardRepositoryStub implements UserScoreBoardRepository {

    private ConcurrentHashMap<MutablePair<Benutzer.Id, TenantId>, Integer> scoreBoard = new ConcurrentHashMap<>();

    @Override
    public void zähleNeuesLied(Benutzer.Id benutzerId, TenantId tenant) {
        scoreBoard.entrySet().stream()
                .filter(entry -> entry.getKey().getLeft().equals(benutzerId))
                .filter(entry -> entry.getKey().getRight().equals(tenant))
                .findFirst()
                .ifPresentOrElse(
                        entry -> scoreBoard.put(entry.getKey(), entry.getValue() + 1),
                        () -> scoreBoard.put(new MutablePair<>(benutzerId, tenant), 1)
                );
    }

    @Override
    public Benutzer.Id höchstePunktZahl() {
        return scoreBoard.entrySet().stream()
                .max(comparingInt(Map.Entry::getValue))
                .map(mutablePairIntegerEntry -> mutablePairIntegerEntry.getKey().getLeft())
                .orElse(null);
    }

    @Override
    public void löscheDatenbank(TenantId tenantId) {
        for (Map.Entry<MutablePair<Benutzer.Id, TenantId>, Integer> mutablePairIntegerEntry : scoreBoard.entrySet()) {
            if (mutablePairIntegerEntry.getKey().getRight().equals(tenantId)) {
                scoreBoard.remove(mutablePairIntegerEntry.getKey());
            }
        }
    }
}
