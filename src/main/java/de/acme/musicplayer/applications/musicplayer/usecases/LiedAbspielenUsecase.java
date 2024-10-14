package de.acme.musicplayer.applications.musicplayer.usecases;

import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;

import java.io.InputStream;

public interface LiedAbspielenUsecase {
    InputStream liedStreamen(Lied.Id liedId, TenantId tenantId);
}
