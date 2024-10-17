package de.acme.musicplayer.applications.users.domain;

import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.applications.users.ports.BenutzerPort;
import de.acme.musicplayer.applications.users.usecases.BenutzerRegistrierenUsecase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class BenutzerRegistrierenService implements BenutzerRegistrierenUsecase {

    private final BenutzerPort benutzerPort;

    public BenutzerRegistrierenService(BenutzerPort benutzerPort) {
        this.benutzerPort = benutzerPort;
    }

    @Override
    @Transactional
    public Benutzer.Id registriereBenutzer(BenutzerRegistrierenCommand benutzerRegistrierenCommand) {
        Benutzer benutzer = new Benutzer(benutzerRegistrierenCommand.name(), benutzerRegistrierenCommand.passwort(), benutzerRegistrierenCommand.email());
        Benutzer.Id id = benutzerPort.benutzerHinzufügen(benutzer, benutzerRegistrierenCommand.tenant());
        log.info("Benutzer {} registriert, ID: {}", benutzer.getName().benutzername, id.Id());
        //TODO: Benutzer wurde registriert Event auslösen
        return id;
    }
}
