package de.acme.musicplayer.components.musicplayer.domain;

import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.TenantId;
import de.acme.musicplayer.components.musicplayer.domain.model.Lied;
import de.acme.musicplayer.components.musicplayer.ports.LiedPort;
import de.acme.musicplayer.components.musicplayer.usecases.LiederAuflistenUsecase;

import java.util.Collection;

public class LiederAuflistenService implements LiederAuflistenUsecase {

    private final LiedPort liedPort;

    public LiederAuflistenService(LiedPort liedPort) {
        this.liedPort = liedPort;
    }


    @Override
    public Collection<Lied> liederAuflisten(TenantId tenantId, BenutzerId benutzerId) {
        return liedPort.listeLiederAuf(benutzerId, tenantId);
    }
}
