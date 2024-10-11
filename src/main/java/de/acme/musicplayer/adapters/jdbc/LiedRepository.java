package de.acme.musicplayer.adapters.jdbc;

import de.acme.jooq.tables.records.LiedRecord;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.ports.LiedPort;
import org.jooq.DSLContext;

import java.util.UUID;

import static de.acme.jooq.Tables.LIED;


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
    public Lied.Id fügeLiedHinzu(Lied lied) {
        String liedId = UUID.randomUUID().toString();
        dslContext.insertInto(LIED, LIED.ID, LIED.TITEL)
                .values(liedId, lied.getTitel()).execute();
        return new Lied.Id(liedId);

    }

    @Override
    public void löscheDatenbank() {
        dslContext.truncate(LIED).cascade().execute();
    }
}
