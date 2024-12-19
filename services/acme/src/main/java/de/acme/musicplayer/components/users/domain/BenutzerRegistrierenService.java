package de.acme.musicplayer.components.users.domain;

import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.components.users.domain.model.Benutzer;
import de.acme.musicplayer.components.users.ports.BenutzerPort;
import de.acme.musicplayer.components.users.usecases.BenutzerRegistrierenUsecase;
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
    public BenutzerId registriereBenutzer(BenutzerRegistrierenCommand benutzerRegistrierenCommand) {
        Benutzer benutzer = new Benutzer(benutzerRegistrierenCommand.name(), benutzerRegistrierenCommand.passwort(), benutzerRegistrierenCommand.email());
        BenutzerId benutzerId = benutzerPort.benutzerHinzufügen(benutzer, benutzerRegistrierenCommand.tenant());
        log.info("Benutzer {} registriert, ID: {}", benutzer.getName().benutzername, benutzerId.Id());
        //TODO: Benutzer wurde registriert Event auslösen
        return benutzerId;
    }
}
