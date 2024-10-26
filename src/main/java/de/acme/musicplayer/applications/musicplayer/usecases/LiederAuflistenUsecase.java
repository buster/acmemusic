package de.acme.musicplayer.applications.musicplayer.usecases;

import de.acme.musicplayer.ModuleApi;
import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.TenantId;

import java.util.Collection;
@ModuleApi
public interface LiederAuflistenUsecase {

    Collection<Lied> liederAuflisten(TenantId tenantId, BenutzerId benutzerId);
}
