package de.acme.musicplayer.applications.musicplayer.usecases;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.TenantId;

import java.io.InputStream;

@ModuleApi
public interface LiedAbspielenUsecase {
    InputStream liedStreamen(LiedId liedId, TenantId tenantId);
}
