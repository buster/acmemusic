package de.acme.musicplayer.applications.users.adapters.jdbc.benutzer;

import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import de.acme.musicplayer.applications.users.ports.BenutzerPort;
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
        dslContext.deleteFrom(BENUTZER.where(BENUTZER.TENANT.eq(tenantId.value()))).execute();
    }
}
