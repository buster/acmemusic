package de.acme.musicplayer.applications.musicplayer.adapters.jdbc.lied;

import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.domain.model.LiedAuszeichnung;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import de.acme.musicplayer.applications.musicplayer.ports.LiedPort;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.UUID;

import static de.acme.jooq.Tables.LIED;
import static de.acme.jooq.Tables.LIED_AUSZEICHNUNGEN;

@Component
public class LiedRepository implements LiedPort {

    private final DSLContext dslContext;

    public LiedRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public long zähleLieder(TenantId tenantId) {
        return dslContext.fetchCount(LIED.where(LIED.TENANT.eq(tenantId.value())));
    }

    @Override
    public Lied.Id fügeLiedHinzu(Lied lied, InputStream inputStream, TenantId tenantId) throws IOException {
        String liedId = UUID.randomUUID().toString();
        dslContext.insertInto(LIED, LIED.ID, LIED.TITEL, LIED.BYTES, LIED.TENANT, LIED.BESITZER_ID)
                .values(liedId, lied.getTitel(), inputStream.readAllBytes(), tenantId.value(), lied.getBesitzer().Id()).execute();
        return new Lied.Id(liedId);

    }

    @Override
    public void löscheDatenbank(TenantId tenantId) {
        dslContext.deleteFrom(LIED.where(LIED.TENANT.eq(tenantId.value()))).execute();
    }

    @Override
    public InputStream ladeLiedStream(Lied.Id liedId, TenantId tenantId) {
        return new ByteArrayInputStream(dslContext.select(LIED.BYTES)
                .from(LIED)
                .where(LIED.ID.eq(liedId.id()))
                .and(LIED.TENANT.eq(tenantId.value()))
                .fetchOne()
                .value1());
    }

    @Override
    public Collection<Lied> listeLiederAuf(Benutzer.Id benutzerId, TenantId tenantId) {
        return dslContext.selectFrom(LIED)
                .where(LIED.TENANT.eq(tenantId.value()).and(LIED.BESITZER_ID.eq(benutzerId.Id())))
                .stream()
                .map(s -> new Lied(new Lied.Id(s.getId()), new Lied.Titel(s.getTitel()), new Benutzer.Id(s.getBesitzerId())))
                .toList();
    }

    @Override
    public Lied leseLied(Lied.Id id, TenantId tenantId) {
        Result<Record> records = dslContext.select().from(LIED.leftOuterJoin(LIED_AUSZEICHNUNGEN).on(LIED_AUSZEICHNUNGEN.TENANT.eq(tenantId.value()).and(LIED_AUSZEICHNUNGEN.LIEDID.eq(id.id()))))
                .fetch();

        Lied lied = new Lied(new Lied.Id(records.get(0).get(LIED.ID)), new Lied.Titel(records.get(0).get(LIED.TITEL)), new Benutzer.Id(records.get(0).get(LIED.BESITZER_ID)));
        lied.setAuszeichnungen(records.stream().map(r -> LiedAuszeichnung.valueOf(r.get(LIED_AUSZEICHNUNGEN.AUSZEICHNUNG))).toList());
        return lied;
    }
}
