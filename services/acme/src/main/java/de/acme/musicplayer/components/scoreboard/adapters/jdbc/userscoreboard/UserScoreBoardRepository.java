package de.acme.musicplayer.components.scoreboard.adapters.jdbc.userscoreboard;

import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.scoreboard.ports.UserScoreBoardPort;
import jakarta.annotation.Nullable;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.springframework.stereotype.Component;

import static de.acme.jooq.tables.BenutzerScoreBoard.BENUTZER_SCORE_BOARD;

@Component
public class UserScoreBoardRepository implements UserScoreBoardPort {

    private final DSLContext dslContext;

    public UserScoreBoardRepository(DSLContext dslContext) {
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
    public @Nullable BenutzerId findeBenutzerMitHöchsterPunktZahl(TenantId tenantId) {
        Record1<String> one = dslContext.select(BENUTZER_SCORE_BOARD.BENUTZERID)
                .from(BENUTZER_SCORE_BOARD)
                .where(BENUTZER_SCORE_BOARD.TENANT.eq(tenantId.value()))
                .orderBy(BENUTZER_SCORE_BOARD.LIEDER.desc())
                .limit(1)
                .fetchOne();
        if (one == null) {
            return null;
        }
        return new BenutzerId(one.value1());
    }

    @Override
    public void löscheDatenbank(TenantId tenantId) {
        dslContext.deleteFrom(BENUTZER_SCORE_BOARD)
                .where(BENUTZER_SCORE_BOARD.TENANT.eq(tenantId.value()))
                .execute();
    }
}
