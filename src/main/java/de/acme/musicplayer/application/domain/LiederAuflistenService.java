package de.acme.musicplayer.application.domain;

import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.TenantId;
import de.acme.musicplayer.application.ports.LiedPort;
import de.acme.musicplayer.application.usecases.LiederAuflistenUsecase;

import java.util.Collection;

public class LiederAuflistenService implements LiederAuflistenUsecase {

    private final LiedPort liedPort;

    public LiederAuflistenService(LiedPort liedPort) {
        this.liedPort = liedPort;
    }


    @Override
    public Collection<Lied.Id> liederAuflisten(TenantId tenantId) {
        return liedPort.listeLiederAuf(tenantId);
    }
}
