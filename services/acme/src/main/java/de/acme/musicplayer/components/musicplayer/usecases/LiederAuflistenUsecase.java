package de.acme.musicplayer.components.musicplayer.usecases;

import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.TenantId;
import de.acme.musicplayer.components.musicplayer.domain.model.Lied;
import de.acme.support.ModuleApi;

import java.util.Collection;

@ModuleApi
public interface LiederAuflistenUsecase {

    Collection<Lied> liederAuflisten(TenantId tenantId, BenutzerId benutzerId);
}
