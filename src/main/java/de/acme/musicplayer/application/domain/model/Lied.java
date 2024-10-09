package de.acme.musicplayer.application.domain.model;


import java.util.Objects;

public class Lied {

    private final Titel titel;
    private LiedId id;

    public Lied(LiedId id, Titel titel) {
        this.id = id;
        this.titel = titel;
    }

    public Lied(Titel titel) {
        this.titel = titel;
    }

    public LiedId getId() {
        return id;
    }

    public void setId(LiedId id) {
        this.id = id;
    }

    public String getTitel() {
        return titel.titel;
    }

    public record LiedId(String id) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LiedId liedId = (LiedId) o;
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
