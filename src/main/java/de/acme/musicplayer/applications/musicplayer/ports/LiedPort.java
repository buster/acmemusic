package de.acme.musicplayer.applications.musicplayer.ports;

import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public interface LiedPort {

    long zähleLieder(TenantId tenantId);

    Lied.Id fügeLiedHinzu(Lied lied, InputStream inputStream, TenantId tenantId) throws IOException;

    void löscheDatenbank(TenantId tenantId);

    InputStream ladeLiedStream(Lied.Id liedId, TenantId tenantId);

    Collection<Lied> listeLiederAuf(TenantId tenantId);
}
