package de.acme.musicplayer.applications.users.adapters.jdbc.benutzer;

import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import de.acme.musicplayer.applications.users.ports.BenutzerPort;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BenutzerPortStub implements BenutzerPort {

    private final Map<Pair<String, TenantId>, Benutzer> benutzerList = new ConcurrentHashMap<>();

    @Override
    public Benutzer.Id benutzerHinzufügen(Benutzer benutzer, TenantId tenantId) {
        String id = UUID.randomUUID().toString();
        benutzer.setId(new Benutzer.Id(id));
        benutzerList.put(new ImmutablePair<>(id, tenantId), benutzer);
        return benutzer.getId();
    }

    @Override
    public long zaehleBenutzer(TenantId tenantId) {
        return benutzerList.keySet().stream()
                .filter(stringTenantIdPair -> stringTenantIdPair.getRight().equals(tenantId))
                .count();
    }

    @Override
    public void loescheDatenbank(TenantId tenantId) {
        for (Pair<String, TenantId> stringTenantIdPair : benutzerList.keySet()) {
            if (stringTenantIdPair.getRight().equals(tenantId)) {
                benutzerList.remove(stringTenantIdPair);
            }
        }
    }

    @Override
    public Benutzer leseBenutzer(Benutzer.Id id, TenantId tenantId) {
        return benutzerList.get(new ImmutablePair<>(id.Id(), tenantId));
    }
}
