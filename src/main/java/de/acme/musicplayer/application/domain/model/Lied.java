package de.acme.musicplayer.application.domain.model;


import java.net.URI;
import java.util.Objects;

public class Lied {

    private final String titel;
    private String interpret;
    private String album;
    private String genre;
    private String erscheinungsjahr;
    private URI uri;
    private LiedId id;

    public Lied(LiedId id, String titel) {
        this.id = id;
        this.titel = titel;
    }

    public Lied(String titel, String interpret, String album, String genre, String erscheinungsjahr, URI uri) {
        this.titel = titel;
        this.interpret = interpret;
        this.album = album;
        this.genre = genre;
        this.erscheinungsjahr = erscheinungsjahr;
        this.uri = uri;
    }

    public LiedId getId() {
        return id;
    }

    public void setId(LiedId id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
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
}
