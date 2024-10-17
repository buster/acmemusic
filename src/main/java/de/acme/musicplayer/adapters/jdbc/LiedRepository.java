package de.acme.musicplayer.adapters.jdbc;

import de.acme.jooq.tables.records.LiedRecord;
import de.acme.musicplayer.application.domain.model.Lied;
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
    public Lied ladeLied(Lied.Id liedId) {
        LiedRecord liedRecord = dslContext.selectFrom(LIED)
                .where(LIED.ID.eq(liedId.id()))
                .fetchOne();
        return new Lied(new Lied.Id(liedRecord.getId()), new Lied.Titel(liedRecord.getTitel()));
    }

    @Override
    public long zähleLieder() {
        return 0L;
    }

    @Override
    public Lied.Id fügeLiedHinzu(Lied lied, InputStream inputStream) throws IOException {
        String liedId = UUID.randomUUID().toString();
        dslContext.insertInto(LIED, LIED.ID, LIED.TITEL, LIED.BYTES)
                .values(liedId, lied.getTitel(), inputStream.readAllBytes()).execute();
        return new Lied.Id(liedId);

    }

    @Override
    public void löscheDatenbank() {
        dslContext.truncate(LIED).cascade().execute();
    }

    @Override
    public InputStream ladeLiedStream(Lied.Id liedId) {
        return new ByteArrayInputStream(dslContext.select(LIED.BYTES)
                .from(LIED)
                .where(LIED.ID.eq(liedId.id()))
                .fetchOne()
                .value1());
    }
}
