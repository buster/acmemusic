package de.acme.musicplayer.applications.musicplayer.domain.model;


import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.LiedId;
import de.acme.musicplayer.common.TenantId;

import java.util.Collection;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class Lied {

    private final Titel titel;
    private final BenutzerId besitzer;
    private final TenantId tenantId;
    private LiedId liedId;
    private Collection<LiedAuszeichnung> auszeichnungen;

    public Lied(LiedId liedId, Titel titel, BenutzerId besitzerId, TenantId tenantId) {
        this.liedId = liedId;
        this.titel = titel;
        this.besitzer = besitzerId;
        this.tenantId = tenantId;
    }

    public Lied(Titel titel, BenutzerId besitzer, TenantId tenantId) {
        this.titel = titel;
        this.besitzer = besitzer;
        this.tenantId = tenantId;
    }

    public static Lied neuesLied(Titel titel, BenutzerId besitzer, TenantId tenantId) {
        Lied lied = new Lied(new LiedId(UUID.randomUUID().toString()), titel, besitzer, tenantId);
        return lied;
    }

    public LiedId getId() {
        return liedId;
    }

    public void setId(LiedId liedId) {
        this.liedId = liedId;
    }

    public String getTitel() {
        return titel.titel;
    }

    public BenutzerId getBesitzer() {
        return besitzer;
    }

    public Collection<LiedAuszeichnung> getAuszeichnungen() {
        return auszeichnungen;
    }

    public void setAuszeichnungen(Collection<LiedAuszeichnung> auszeichnungen) {
        this.auszeichnungen = auszeichnungen;
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
