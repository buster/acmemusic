package de.acme.musicplayer.application.usecases;

import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.TenantId;

import java.util.Collection;

public interface LiederAuflistenUsecase {

    Collection<Lied.Id> liederAuflisten(TenantId tenantId);
}
