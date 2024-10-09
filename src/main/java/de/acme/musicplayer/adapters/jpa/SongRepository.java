package de.acme.musicplayer.adapters.jpa;

import de.acme.jooq.Tables;
import de.acme.jooq.tables.records.SongRecord;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.ports.LiedPort;
import org.jooq.DSLContext;

import java.util.UUID;

import static de.acme.jooq.tables.Song.SONG;

public class SongRepository implements LiedPort {

    private final SongJpaRepository jpaRepo;
    private final DSLContext dslContext;

    public SongRepository(SongJpaRepository jpaRepo, DSLContext dslContext) {
        this.jpaRepo = jpaRepo;
        this.dslContext = dslContext;
    }

    @Override
    public Lied ladeLied(String songId) {
        SongRecord songRecord = dslContext.selectFrom(SONG)
                .where(SONG.ID.eq(songId))
                .fetchOne();
        Lied lied = new Lied(new Lied.LiedId(songRecord.getId()), songRecord.getTitel());
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
