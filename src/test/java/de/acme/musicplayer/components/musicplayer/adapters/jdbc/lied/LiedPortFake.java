package de.acme.musicplayer.components.musicplayer.adapters.jdbc.lied;

import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.TenantId;
import de.acme.musicplayer.components.musicplayer.domain.model.Lied;
import de.acme.musicplayer.components.musicplayer.ports.LiedPort;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LiedPortFake implements LiedPort {

    private final Map<Pair<String, TenantId>, Lied> lieder = new ConcurrentHashMap<>();
    private final Map<Pair<String, TenantId>, byte[]> bytestreams = new ConcurrentHashMap<>();

    private static ImmutablePair<String, TenantId> tableKey(LiedId liedId, TenantId tenantId) {
        return new ImmutablePair<>(liedId.id(), tenantId);
    }

    @Override
    public LiedId fügeLiedHinzu(Lied lied, InputStream inputStream) throws IOException {
        ImmutablePair<String, TenantId> k = tableKey(lied.getId(), lied.getTenantId());
        bytestreams.put(k, inputStream.readAllBytes());
        lieder.put(k, lied);
        return lied.getId();
    }

    @Override
    public void löscheDatenbank(TenantId tenantId) {
        for (Pair<String, TenantId> stringTenantIdPair : lieder.keySet()) {
            if (stringTenantIdPair.getRight().equals(tenantId)) {
                lieder.remove(stringTenantIdPair);
            }
        }
    }

    @Override
    public InputStream ladeLiedStream(LiedId liedId, TenantId tenantId) {
        return new ByteArrayInputStream(bytestreams.get(tableKey(liedId, tenantId)));
    }

    @Override
    public Collection<Lied> listeLiederAuf(BenutzerId benutzerId, TenantId tenantId) {
        return lieder.entrySet().stream()
                .filter(entry -> entry.getKey().getRight().equals(tenantId))
                .map(Map.Entry::getValue)
                .filter(lied -> lied.getBesitzer().equals(benutzerId))
                .toList();
    }

}
