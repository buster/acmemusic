package de.acme.musicplayer.applications.users.adapters.jdbc.benutzer;

import de.acme.jooq.tables.records.BenutzerAuszeichnungenRecord;
import de.acme.jooq.tables.records.BenutzerRecord;
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

import static com.google.common.base.Preconditions.checkState;
import static de.acme.jooq.Tables.*;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

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
        return  dslContext.fetchCount(BENUTZER.where(BENUTZER.TENANT.eq(tenantId.value())));
    }

    @Override
    public void loescheDatenbank(TenantId tenantId) {
        dslContext.deleteFrom(BENUTZER_AUSZEICHNUNGEN.where(BENUTZER_AUSZEICHNUNGEN.TENANT.eq(tenantId.value()))).execute();
        dslContext.deleteFrom(BENUTZER.where(BENUTZER.TENANT.eq(tenantId.value()))).execute();
    }

    @Override
    public Benutzer leseBenutzer(Benutzer.Id id, TenantId tenantId) {
        BenutzerRecord benutzerRecord = dslContext.selectFrom(BENUTZER.where(BENUTZER.ID.eq(id.Id())
                        .and(BENUTZER.TENANT.eq(tenantId.value()))))
                .fetchOne();
        checkState(benutzerRecord != null, "Benutzer nicht gefunden");

        Benutzer benutzer = new Benutzer(
                new Benutzer.Name(benutzerRecord.getName()),
                new Benutzer.Passwort(benutzerRecord.getPasswort()),
                new Benutzer.Email(benutzerRecord.getEmail()));
        benutzer.setId(new Benutzer.Id(benutzerRecord.getId()));

        Result<BenutzerAuszeichnungenRecord> auszeichnungen = dslContext.selectFrom(BENUTZER_AUSZEICHNUNGEN.where(BENUTZER_AUSZEICHNUNGEN.BENUTZER.eq(id.Id())
                .and(BENUTZER_AUSZEICHNUNGEN.TENANT.eq(tenantId.value())))).fetch();

        if (isNotEmpty(auszeichnungen)) {
            benutzer.setAuszeichnungen(
                    auszeichnungen.stream()
                            .map(r -> Auszeichnung.valueOf(r.getAuszeichnung()))
                            .collect(Collectors.toSet()));
        }
        return benutzer;
    }

    @Override
    public void speichereBenutzer(Benutzer benutzer, TenantId tenant) {
        dslContext.deleteFrom(BENUTZER_AUSZEICHNUNGEN)
                .where(BENUTZER_AUSZEICHNUNGEN.BENUTZER.eq(benutzer.getId().Id()))
                .and(BENUTZER_AUSZEICHNUNGEN.TENANT.eq(tenant.value()))
                .execute();

        int updatedRecords = dslContext.update(BENUTZER
                        .where(BENUTZER.ID.eq(benutzer.getId().Id())
                                .and(BENUTZER.TENANT.eq(tenant.value()))))
                .set(BENUTZER.NAME, benutzer.getName().benutzername)
                .set(BENUTZER.PASSWORT, benutzer.getPasswort().passwort)
                .set(BENUTZER.EMAIL, benutzer.getEmail().email)
                .execute();
        checkState(updatedRecords == 1, "Benutzer nicht gefunden");

        for (Auszeichnung auszeichnung : benutzer.getAuszeichnungen()) {
            dslContext.insertInto(BENUTZER_AUSZEICHNUNGEN)
                    .set(BENUTZER_AUSZEICHNUNGEN.BENUTZER, benutzer.getId().Id())
                    .set(BENUTZER_AUSZEICHNUNGEN.TENANT, tenant.value())
                    .set(BENUTZER_AUSZEICHNUNGEN.AUSZEICHNUNG, auszeichnung.name())
                    .execute();
        }
    }
}
