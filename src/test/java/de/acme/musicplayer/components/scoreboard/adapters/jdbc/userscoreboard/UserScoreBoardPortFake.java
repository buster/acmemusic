package de.acme.musicplayer.components.scoreboard.adapters.jdbc.userscoreboard;

import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.TenantId;
import de.acme.musicplayer.components.scoreboard.ports.UserScoreBoardPort;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Comparator.comparingInt;

public class UserScoreBoardPortFake implements UserScoreBoardPort {

    private final ConcurrentHashMap<MutablePair<BenutzerId, TenantId>, Integer> scoreBoard = new ConcurrentHashMap<>();

    @Override
    public void zähleNeuesLied(BenutzerId benutzerId, TenantId tenant) {
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
    public BenutzerId höchstePunktZahl(TenantId tenantId) {
        return scoreBoard.entrySet().stream()
                .filter(entry -> entry.getKey().getRight().equals(tenantId))
                .max(comparingInt(Map.Entry::getValue))
                .map(mutablePairIntegerEntry -> mutablePairIntegerEntry.getKey().getLeft())
                .orElse(null);
    }

    @Override
    public void löscheDatenbank(TenantId tenantId) {
        for (Map.Entry<MutablePair<BenutzerId, TenantId>, Integer> mutablePairIntegerEntry : scoreBoard.entrySet()) {
            if (mutablePairIntegerEntry.getKey().getRight().equals(tenantId)) {
                scoreBoard.remove(mutablePairIntegerEntry.getKey());
            }
        }
    }
}
