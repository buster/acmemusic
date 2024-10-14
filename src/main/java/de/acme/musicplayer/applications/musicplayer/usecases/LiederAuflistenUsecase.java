package de.acme.musicplayer.applications.musicplayer.usecases;

import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;

import java.util.Collection;

public interface LiederAuflistenUsecase {

    Collection<Lied> liederAuflisten(TenantId tenantId);
}
