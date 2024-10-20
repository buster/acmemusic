package de.acme.musicplayer.adapters.jdbc;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.TenantId;
import de.acme.musicplayer.application.ports.BenutzerPort;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static de.acme.jooq.Tables.BENUTZER;

@Component
public class BenutzerRepository implements BenutzerPort {

    private final DSLContext dslContext;

    public BenutzerRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public Benutzer.Id benutzerHinzufügen(Benutzer benutzer, TenantId tenantId) {
        Benutzer.Id id = new Benutzer.Id(UUID.randomUUID().toString());
        dslContext.insertInto(BENUTZER, BENUTZER.ID, BENUTZER.NAME, BENUTZER.PASSWORT, BENUTZER.EMAIL, BENUTZER.TENANT)
                .values(id.Id(), benutzer.getName().benutzername, benutzer.getPasswort().passwort, benutzer.getEmail().email, tenantId.value())
                .execute();
        return id;
    }

    @Override
    public long zaehleBenutzer(TenantId tenantId) {
        return

                dslContext.fetchCount(BENUTZER.where(BENUTZER.TENANT.eq(tenantId.value())));
    }

    @Override
    public void loescheDatenbank(TenantId tenantId) {
        dslContext.truncate(BENUTZER.where(BENUTZER.TENANT.eq(tenantId.value()))).cascade().execute();
    }
}
