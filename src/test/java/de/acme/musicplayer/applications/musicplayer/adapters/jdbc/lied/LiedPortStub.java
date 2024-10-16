package de.acme.musicplayer.applications.musicplayer.adapters.jdbc.lied;

import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import de.acme.musicplayer.applications.musicplayer.ports.LiedPort;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LiedPortStub implements LiedPort {

    private final Map<Pair<String, TenantId>, Lied> lieder = new ConcurrentHashMap<>();
    private final Map<Pair<String, TenantId>, byte[]> bytestreams = new ConcurrentHashMap<>();

    private static ImmutablePair<String, TenantId> tableKey(Lied.Id liedId, TenantId tenantId) {
        return new ImmutablePair<>(liedId.id(), tenantId);
    }

    @Override
    public long zähleLieder(TenantId tenantId) {
        return lieder.size();
    }

    @Override
    public Lied.Id fügeLiedHinzu(Lied lied, InputStream inputStream, TenantId tenantId) throws IOException {
        Lied.Id id = new Lied.Id(UUID.randomUUID().toString());
        lied.setId(id);
        ImmutablePair<String, TenantId> k = tableKey(lied.getId(), tenantId);
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
    public InputStream ladeLiedStream(Lied.Id liedId, TenantId tenantId) {
        return new ByteArrayInputStream(bytestreams.get(tableKey(liedId, tenantId)));
    }

    @Override
    public Collection<Lied> listeLiederAuf(Benutzer.Id benutzerId, TenantId tenantId) {
        return lieder.entrySet().stream()
                .filter(entry -> entry.getKey().getRight().equals(tenantId))
                .map(Map.Entry::getValue)
                .filter(lied -> lied.getBesitzer().equals(benutzerId))
                .toList();
    }
}
