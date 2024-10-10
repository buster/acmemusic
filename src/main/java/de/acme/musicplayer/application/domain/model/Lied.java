package de.acme.musicplayer.application.domain.model;


import java.util.Objects;

public class Lied {

    private final Titel titel;
    private Id id;

    public Lied(Id id, Titel titel) {
        this.id = id;
        this.titel = titel;
    }

    public Lied(Titel titel) {
        this.titel = titel;
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

    public record Id(String id) {
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
            this.titel = titel;
        }
    }
}
