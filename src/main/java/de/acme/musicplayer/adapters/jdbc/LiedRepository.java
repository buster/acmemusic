package de.acme.musicplayer.adapters.jdbc;

import de.acme.jooq.tables.records.LiedRecord;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.TenantId;
import de.acme.musicplayer.application.ports.LiedPort;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static de.acme.jooq.Tables.LIED;

@Component
public class LiedRepository implements LiedPort {

    private final DSLContext dslContext;

    public LiedRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public Lied ladeLied(Lied.Id liedId, TenantId tenantId) {
        LiedRecord liedRecord = dslContext.selectFrom(LIED)
                .where(LIED.ID.eq(liedId.id()))
                .fetchOne();
        return new Lied(new Lied.Id(liedRecord.getId()), new Lied.Titel(liedRecord.getTitel()));
    }

    @Override
    public long zähleLieder(TenantId tenantId) {
        return dslContext.fetchCount(LIED.where(LIED.TENANT.eq(tenantId.value())));
    }

    @Override
    public Lied.Id fügeLiedHinzu(Lied lied, InputStream inputStream, TenantId tenantId) throws IOException {
        String liedId = UUID.randomUUID().toString();
        dslContext.insertInto(LIED, LIED.ID, LIED.TITEL, LIED.BYTES, LIED.TENANT)
                .values(liedId, lied.getTitel(), inputStream.readAllBytes(), tenantId.value()).execute();
        return new Lied.Id(liedId);

    }

    @Override
    public void löscheDatenbank(TenantId tenantId) {
        dslContext.truncate(LIED.where(LIED.TENANT.eq(tenantId.value()))).cascade().execute();
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
}
