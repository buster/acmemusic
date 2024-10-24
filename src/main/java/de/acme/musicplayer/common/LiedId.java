package de.acme.musicplayer.common;

import de.acme.musicplayer.ModuleApi;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

@ModuleApi
public record LiedId(String id) {
    public LiedId {
        checkNotNull(id);
    }

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
