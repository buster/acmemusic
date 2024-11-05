package de.acme.musicplayer.components.musicplayer.ports;

import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.TenantId;
import de.acme.musicplayer.components.musicplayer.domain.model.Lied;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public interface LiedPort {

    LiedId fügeLiedHinzu(Lied lied, InputStream inputStream) throws IOException;

    void löscheDatenbank(TenantId tenantId);

    InputStream ladeLiedStream(LiedId liedId, TenantId tenantId);

    Collection<Lied> listeLiederAuf(BenutzerId benutzerId, TenantId tenantId);
}