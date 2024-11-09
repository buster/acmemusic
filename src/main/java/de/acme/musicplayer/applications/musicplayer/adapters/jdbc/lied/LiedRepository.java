package de.acme.musicplayer.applications.musicplayer.adapters.jdbc.lied;

import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.ports.LiedPort;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.TenantId;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import static de.acme.jooq.Tables.LIED;

@Component
public class LiedRepository implements LiedPort {

    private final DSLContext dslContext;

    public LiedRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public LiedId fügeLiedHinzu(Lied lied, InputStream inputStream) throws IOException {
        dslContext.insertInto(LIED, LIED.ID, LIED.TITEL, LIED.BYTES, LIED.TENANT, LIED.BESITZER_ID)
                .values(lied.getId().id(), lied.getTitel(), inputStream.readAllBytes(), lied.getTenantId().value(), lied.getBesitzer().Id()).execute();
        return lied.getId();

    }

    @Override
    public void löscheDatenbank(TenantId tenantId) {
        dslContext.deleteFrom(LIED.where(LIED.TENANT.eq(tenantId.value()))).execute();
    }

    @Override
    public InputStream ladeLiedStream(LiedId liedId, TenantId tenantId) {
        return new ByteArrayInputStream(dslContext.select(LIED.BYTES)
                .from(LIED)
                .where(LIED.ID.eq(liedId.id()))
                .and(LIED.TENANT.eq(tenantId.value()))
                .fetchOne()
                .value1());
    }

    @Override
    public Collection<Lied> listeLiederAuf(BenutzerId benutzerId, TenantId tenantId) {
        return dslContext.selectFrom(LIED)
                .where(LIED.TENANT.eq(tenantId.value()).and(LIED.BESITZER_ID.eq(benutzerId.Id())))
                .stream()
                .map(s -> new Lied(new LiedId(s.getId()), new Lied.Titel(s.getTitel()), new BenutzerId(s.getBesitzerId()), new TenantId(s.getTenant())))
                .toList();
    }
}
