package de.acme.musicplayer.adapters.jdbc;

import de.acme.jooq.Tables;
import de.acme.jooq.tables.records.SongRecord;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.ports.LiedPort;
import org.jooq.DSLContext;

import java.util.UUID;

import static de.acme.jooq.tables.Song.SONG;

public class SongRepository implements LiedPort {

    private final DSLContext dslContext;

    public SongRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public Lied ladeLied(Lied.LiedId songId) {
        SongRecord songRecord = dslContext.selectFrom(SONG)
                .where(SONG.ID.eq(songId.id()))
                .fetchOne();
        Lied lied = new Lied(new Lied.LiedId(songRecord.getId()), new Lied.Titel(songRecord.getTitel()));
        return lied;
    }

    @Override
    public long zähleLieder() {
        return 0L;
    }

    @Override
    public Lied.LiedId fügeLiedHinzu(Lied lied) {
        String liedId = UUID.randomUUID().toString();
        dslContext.insertInto(Tables.SONG, SONG.ID, SONG.TITEL)
                .values(liedId, lied.getTitel()).execute();
        return new Lied.LiedId(liedId);

    }
}
