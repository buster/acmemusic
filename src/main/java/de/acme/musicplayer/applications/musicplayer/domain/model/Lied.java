package de.acme.musicplayer.applications.musicplayer.domain.model;


import de.acme.musicplayer.applications.musicplayer.ports.EventPublisher;
import de.acme.musicplayer.applications.users.domain.model.Benutzer;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class Lied {

    private final Titel titel;
    private final Benutzer.Id besitzer;
    private final TenantId tenantId;
    private Id id;
    private Collection<LiedAuszeichnung> auszeichnungen;

    public Lied(Id id, Titel titel, Benutzer.Id besitzerId, TenantId tenantId) {
        this.id = id;
        this.titel = titel;
        this.besitzer = besitzerId;
        this.tenantId = tenantId;
    }

    public Lied(Titel titel, Benutzer.Id besitzer, TenantId tenantId) {
        this.titel = titel;
        this.besitzer = besitzer;
        this.tenantId = tenantId;
    }

    public static Lied neuesLied(Titel titel, Benutzer.Id besitzer, TenantId tenantId) {
        Lied lied = new Lied(new Id(UUID.randomUUID().toString()), titel, besitzer, tenantId);
        return lied;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public String getTitel() {
        return titel.titel;
    }

    public Benutzer.Id getBesitzer() {
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

    public record Id(String id) {
        public Id(String id) {
            checkNotNull(id);
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Id liedId = (Id) o;
            return Objects.equals(id, liedId.id);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }
    }

    public static class Titel {
        private final String titel;

        public Titel(String titel) {
            checkNotNull(titel);
            this.titel = titel;
        }
    }
}
