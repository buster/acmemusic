package de.acme.musicplayer.applications.musicplayer.usecases;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.TenantId;

import java.io.IOException;
import java.io.InputStream;
@ModuleApi
public interface LiedHochladenUsecase {

    LiedId liedHochladen(BenutzerId benutzerId, Lied.Titel title, InputStream byteStream, TenantId tenantId) throws IOException;
}
