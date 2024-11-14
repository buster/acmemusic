package de.acme.musicplayer.components.users.adapters.jdbc.benutzer;

import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.users.domain.model.Benutzer;
import de.acme.musicplayer.components.users.ports.BenutzerPort;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BenutzerPortFake implements BenutzerPort {

    private final Map<Pair<BenutzerId, TenantId>, Benutzer> benutzerList = new ConcurrentHashMap<>();

    @Override
    public BenutzerId benutzerHinzufügen(Benutzer benutzer, TenantId tenantId) {
        BenutzerId benutzerId = new BenutzerId(UUID.randomUUID().toString());
        benutzer.setId(benutzerId);
        benutzerList.put(new ImmutablePair<>(benutzerId, tenantId), benutzer);
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
        for (Pair<BenutzerId, TenantId> stringTenantIdPair : benutzerList.keySet()) {
            if (stringTenantIdPair.getRight().equals(tenantId)) {
                benutzerList.remove(stringTenantIdPair);
            }
        }
    }

    @Override
    public Benutzer leseBenutzer(BenutzerId benutzerId, TenantId tenantId) {
        for (Pair<BenutzerId, TenantId> stringTenantIdPair : benutzerList.keySet()) {
            if (stringTenantIdPair.getRight().equals(tenantId) && stringTenantIdPair.getLeft().equals(benutzerId)) {
                return benutzerList.get(stringTenantIdPair);
            }
        }
        return null;
    }

    @Override
    public void speichereBenutzer(Benutzer benutzer, TenantId tenant) {
        for (Pair<BenutzerId, TenantId> stringTenantIdPair : benutzerList.keySet()) {
            if (stringTenantIdPair.getRight().equals(tenant) && stringTenantIdPair.getLeft().equals(benutzer.getId())) {
                benutzerList.put(stringTenantIdPair, benutzer);
            }
        }
    }
}
