package de.acme.musicplayer.applications.users.adapters.jdbc.benutzer;

import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import de.acme.musicplayer.applications.users.domain.model.Auszeichnung;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.applications.users.ports.BenutzerPort;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

import static de.acme.jooq.Tables.*;

@Component
public class BenutzerRepository implements BenutzerPort {

    private final DSLContext dslContext;

    public BenutzerRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public Benutzer.Id benutzerHinzufügen(Benutzer benutzer, TenantId tenantId) {
        Benutzer.Id id = new Benutzer.Id(UUID.randomUUID().toString());
        dslContext.insertInto(BENUTZER, BENUTZER.ID, BENUTZER.NAME, BENUTZER.PASSWORT, BENUTZER.EMAIL, BENUTZER.TENANT)
                .values(id.Id(), benutzer.getName().benutzername, benutzer.getPasswort().passwort, benutzer.getEmail().email, tenantId.value())
                .execute();
        return id;
    }

    @Override
    public long zaehleBenutzer(TenantId tenantId) {
        return

                dslContext.fetchCount(BENUTZER.where(BENUTZER.TENANT.eq(tenantId.value())));
    }

    @Override
    public void loescheDatenbank(TenantId tenantId) {
        dslContext.deleteFrom(BENUTZER.where(BENUTZER.TENANT.eq(tenantId.value()))).execute();
    }

    @Override
    public Benutzer leseBenutzer(Benutzer.Id id, TenantId tenantId) {
        Result<Record> records = dslContext.select().from(BENUTZER.leftOuterJoin(BENUTZER_AUSZEICHNUNGEN)
                        .on(BENUTZER.ID.eq(BENUTZER_AUSZEICHNUNGEN.BENUTZER).and(BENUTZER.TENANT.eq(BENUTZER_AUSZEICHNUNGEN.TENANT))))
                .fetch();

        Benutzer benutzer = new Benutzer(new Benutzer.Name(records.getFirst().get(BENUTZER.NAME)), new Benutzer.Passwort(records.getFirst().get(BENUTZER.PASSWORT)), new Benutzer.Email(records.getFirst().get(BENUTZER.EMAIL)));
        benutzer.setAuszeichnungen(
                records.stream()
                        .filter(r -> r.get(LIED_AUSZEICHNUNGEN.AUSZEICHNUNG) != null)
                        .map(r ->
                                Auszeichnung.valueOf(r.get(LIED_AUSZEICHNUNGEN.AUSZEICHNUNG))

                        ).collect(Collectors.toSet()));
        return benutzer;
    }
}
