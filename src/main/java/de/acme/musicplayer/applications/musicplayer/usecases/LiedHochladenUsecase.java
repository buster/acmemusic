package de.acme.musicplayer.applications.musicplayer.usecases;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;

import java.io.IOException;
import java.io.InputStream;
@ModuleApi
public interface LiedHochladenUsecase {

    Lied.Id liedHochladen(Lied.Titel title, InputStream byteStream, TenantId tenantId) throws IOException;
}
