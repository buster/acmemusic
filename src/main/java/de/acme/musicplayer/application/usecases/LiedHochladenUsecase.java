package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.TenantId;

import java.io.IOException;
import java.io.InputStream;

public interface LiedHochladenUsecase {

    Lied.Id liedHochladen(Lied.Titel title, InputStream byteStream, TenantId tenantId) throws IOException;
}
