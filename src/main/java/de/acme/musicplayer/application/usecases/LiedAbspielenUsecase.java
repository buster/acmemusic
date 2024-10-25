package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.TenantId;

import java.io.InputStream;

public interface LiedAbspielenUsecase {
    InputStream liedStreamen(Lied.Id liedId, TenantId tenantId);
}
