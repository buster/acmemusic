package de.acme.musicplayer.applications.users.adapters.jdbc.benutzer;

import de.acme.musicplayer.applications.users.domain.BenutzerRegistrierenService;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import de.acme.musicplayer.applications.users.ports.BenutzerPort;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BenutzerPortStub implements BenutzerPort {

    private final Map<Pair<Benutzer.Id, TenantId>, Benutzer> benutzerList = new ConcurrentHashMap<>();

    @Override
    public Benutzer.Id benutzerHinzufügen(Benutzer benutzer, TenantId tenantId) {
        Benutzer.Id id = new Benutzer.Id(UUID.randomUUID().toString());
        benutzer.setId(id);
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
        for (Pair<Benutzer.Id, TenantId> stringTenantIdPair : benutzerList.keySet()) {
            if (stringTenantIdPair.getRight().equals(tenantId)) {
                benutzerList.remove(stringTenantIdPair);
            }
        }
    }

    @Override
    public Benutzer leseBenutzer(Benutzer.Id id, TenantId tenantId) {
        for (Pair<Benutzer.Id, TenantId> stringTenantIdPair : benutzerList.keySet()) {
            if (stringTenantIdPair.getRight().equals(tenantId) && stringTenantIdPair.getLeft().equals(id)) {
                return benutzerList.get(stringTenantIdPair);
            }
        }
        return null;
    }
}
