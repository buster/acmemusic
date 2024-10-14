package de.acme.musicplayer.application.ports;

import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.TenantId;

import java.io.IOException;
import java.io.InputStream;

public interface LiedPort {

    Lied ladeLied(Lied.Id liedId, TenantId tenantId);

    long zähleLieder(TenantId tenantId);

    Lied.Id fügeLiedHinzu(Lied lied, InputStream inputStream, TenantId tenantId) throws IOException;

    void löscheDatenbank(TenantId tenantId);

    InputStream ladeLiedStream(Lied.Id liedId, TenantId tenantId);
}
