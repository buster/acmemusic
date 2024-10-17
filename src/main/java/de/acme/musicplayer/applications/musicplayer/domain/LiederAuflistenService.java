package de.acme.musicplayer.applications.musicplayer.domain;

import de.acme.musicplayer.applications.musicplayer.domain.model.Lied;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;
import de.acme.musicplayer.applications.musicplayer.ports.LiedPort;
import de.acme.musicplayer.applications.musicplayer.usecases.LiederAuflistenUsecase;

import java.util.Collection;

public class LiederAuflistenService implements LiederAuflistenUsecase {

    private final LiedPort liedPort;

    public LiederAuflistenService(LiedPort liedPort) {
        this.liedPort = liedPort;
    }


    @Override
    public Collection<Lied> liederAuflisten(TenantId tenantId) {
        return liedPort.listeLiederAuf(tenantId);
    }
}
