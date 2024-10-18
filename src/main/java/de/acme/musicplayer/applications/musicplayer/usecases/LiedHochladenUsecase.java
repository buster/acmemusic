package de.acme.musicplayer.applications.musicplayer.usecases;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;

import java.io.IOException;
import java.io.InputStream;
@ModuleApi
public interface LiedHochladenUsecase {

    Lied.Id liedHochladen(Benutzer.Id benutzerId, Lied.Titel title, InputStream byteStream, TenantId tenantId) throws IOException;
}
