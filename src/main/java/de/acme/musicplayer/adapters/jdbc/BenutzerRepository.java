package de.acme.musicplayer.adapters.jdbc;

import de.acme.jooq.Tables;
import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.ports.BenutzerPort;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BenutzerRepository implements BenutzerPort {

    private final DSLContext dslContext;

    public BenutzerRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public Benutzer.Id benutzerHinzufügen(Benutzer benutzer) {
        Benutzer.Id id = new Benutzer.Id(UUID.randomUUID().toString());
        dslContext.insertInto(Tables.BENUTZER, Tables.BENUTZER.ID, Tables.BENUTZER.NAME, Tables.BENUTZER.PASSWORT, Tables.BENUTZER.EMAIL)
                .values(id.Id(), benutzer.getName().benutzername, benutzer.getPasswort().passwort, benutzer.getEmail().email)
                .execute();
        return id;
    }

    @Override
    public long zaehleBenutzer() {
        return dslContext.fetchCount(Tables.BENUTZER);
    }

    @Override
    public void loescheDatenbank() {
        dslContext.truncate(Tables.BENUTZER).cascade().execute();
    }
}
