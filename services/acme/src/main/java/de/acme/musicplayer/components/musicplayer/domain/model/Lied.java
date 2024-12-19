package de.acme.musicplayer.components.musicplayer.domain.model;


import de.acme.musicplayer.common.api.BenutzerId;
import de.acme.musicplayer.common.api.LiedId;
import de.acme.musicplayer.common.api.TenantId;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class Lied {

    private final Titel titel;
    private final BenutzerId besitzer;
    private final TenantId tenantId;
    private final LiedId liedId;

    public Lied(LiedId liedId, Titel titel, BenutzerId besitzerId, TenantId tenantId) {
        this.liedId = liedId;
        this.titel = titel;
        this.besitzer = besitzerId;
        this.tenantId = tenantId;
    }

    public static Lied neuesLied(Titel titel, BenutzerId besitzer, TenantId tenantId) {
        return new Lied(new LiedId(UUID.randomUUID().toString()), titel, besitzer, tenantId);
    }

    public LiedId getId() {
        return liedId;
    }

    public String getTitel() {
        return titel.titel;
    }

    public BenutzerId getBesitzer() {
        return besitzer;
    }

    public TenantId getTenantId() {
        return tenantId;
    }

    public record Titel(String titel) {
        public Titel {
            checkNotNull(titel);
        }
    }
}
