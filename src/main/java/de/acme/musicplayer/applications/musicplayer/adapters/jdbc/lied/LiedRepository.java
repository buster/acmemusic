package de.acme.musicplayer.applications.musicplayer.adapters.jdbc.lied;

import de.acme.jooq.tables.records.LiedAuszeichnungenRecord;
import de.acme.jooq.tables.records.LiedRecord;
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

import static com.google.common.base.Preconditions.checkState;
import static de.acme.jooq.Tables.LIED;
import static de.acme.jooq.Tables.LIED_AUSZEICHNUNGEN;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

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
    public Lied.Id fügeLiedHinzu(Lied lied, InputStream inputStream) throws IOException {
        dslContext.insertInto(LIED, LIED.ID, LIED.TITEL, LIED.BYTES, LIED.TENANT, LIED.BESITZER_ID)
                .values(lied.getId().id(), lied.getTitel(), inputStream.readAllBytes(), lied.getTenantId().value(), lied.getBesitzer().Id()).execute();
        return lied.getId();

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
                .map(s -> new Lied(new Lied.Id(s.getId()), new Lied.Titel(s.getTitel()), new Benutzer.Id(s.getBesitzerId()), new TenantId(s.getTenant())))
                .toList();
    }

    @Override
    public Lied leseLied(Lied.Id id, TenantId tenantId) {
        LiedRecord liedRecord = dslContext.selectFrom(LIED)
                .where(LIED.ID.eq(id.id()).and(LIED.TENANT.eq(tenantId.value())))
                .fetchOne();
        checkState(liedRecord != null, "Lied nicht gefunden");

        Lied lied = new Lied(
                new Lied.Id(liedRecord.getId()),
                new Lied.Titel(liedRecord.getTitel()),
                new Benutzer.Id(liedRecord.getBesitzerId()),
                new TenantId(liedRecord.getTenant()));


        Result<LiedAuszeichnungenRecord> liedAuszeichnungen = dslContext.selectFrom(LIED_AUSZEICHNUNGEN
                        .where(LIED_AUSZEICHNUNGEN.LIEDID.eq(id.id())
                                .and(LIED_AUSZEICHNUNGEN.TENANT.eq(tenantId.value()))))
                .fetch();

        if (isNotEmpty(liedAuszeichnungen)) {
            lied.setAuszeichnungen(
                    liedAuszeichnungen.stream()
                            .map(r -> LiedAuszeichnung.valueOf(r.getAuszeichnung()))
                            .toList());
        }

        return lied;
    }
}
