package de.acme.musicplayer.components.musicplayer.usecases;

import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.LiedId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.musicplayer.domain.model.Lied;
import de.acme.support.ModuleApi;

import java.io.IOException;
import java.io.InputStream;

@ModuleApi
public interface LiedHochladenUsecase {

    LiedId liedHochladen(BenutzerId benutzerId, Lied.Titel title, InputStream byteStream, TenantId tenantId) throws IOException;
}
