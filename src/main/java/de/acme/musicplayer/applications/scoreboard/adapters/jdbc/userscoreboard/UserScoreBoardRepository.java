package de.acme.musicplayer.applications.scoreboard.adapters.jdbc.userscoreboard;

import de.acme.musicplayer.applications.scoreboard.ports.UserScoreBoardPort;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.TenantId;
import org.jooq.Record1;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.stereotype.Component;

import static de.acme.jooq.tables.BenutzerScoreBoard.BENUTZER_SCORE_BOARD;

@Component
public class UserScoreBoardRepository implements UserScoreBoardPort {

    private final DefaultDSLContext dslContext;

    public UserScoreBoardRepository(DefaultDSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public void zähleNeuesLied(BenutzerId benutzerId, TenantId tenant) {
        dslContext.insertInto(BENUTZER_SCORE_BOARD)
                .set(BENUTZER_SCORE_BOARD.BENUTZERID, benutzerId.Id())
                .set(BENUTZER_SCORE_BOARD.TENANT, tenant.value())
                .set(BENUTZER_SCORE_BOARD.LIEDER, 1)
                .onDuplicateKeyUpdate()
                .set(BENUTZER_SCORE_BOARD.LIEDER, BENUTZER_SCORE_BOARD.LIEDER.plus(1))
                .execute();

    }

    @Override
    public BenutzerId höchstePunktZahl(TenantId tenantId) {
        Record1<String> one = dslContext.select(BENUTZER_SCORE_BOARD.BENUTZERID)
                .from(BENUTZER_SCORE_BOARD)
                .where(BENUTZER_SCORE_BOARD.TENANT.eq(tenantId.value()))
                .orderBy(BENUTZER_SCORE_BOARD.LIEDER.desc())
                .limit(1)
                .fetchOne();
        if (one == null) { return null; }
        return new BenutzerId(one.value1());
    }

    @Override
    public void löscheDatenbank(TenantId tenantId) {
        dslContext.deleteFrom(BENUTZER_SCORE_BOARD)
                .where(BENUTZER_SCORE_BOARD.TENANT.eq(tenantId.value()))
                .execute();
    }
}
