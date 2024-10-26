package de.acme.musicplayer.applications.musicplayer.ports;

import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.TenantId;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public interface LiedPort {

    long zähleLieder(TenantId tenantId);

    LiedId fügeLiedHinzu(Lied lied, InputStream inputStream) throws IOException;

    void löscheDatenbank(TenantId tenantId);

    InputStream ladeLiedStream(LiedId liedId, TenantId tenantId);

    Collection<Lied> listeLiederAuf(BenutzerId benutzerId, TenantId tenantId);

    Lied leseLied(LiedId liedId, TenantId tenantId);
}
