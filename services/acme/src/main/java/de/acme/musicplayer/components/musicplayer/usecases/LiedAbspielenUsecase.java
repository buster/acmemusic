package de.acme.musicplayer.components.musicplayer.usecases;

import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.TenantId;
import de.acme.support.ModuleApi;

import java.io.InputStream;

@ModuleApi
public interface LiedAbspielenUsecase {
    InputStream liedStreamen(LiedId liedId, TenantId tenantId);
}
