package de.acme.musicplayer.cucumber.stubtesting;

import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.ports.LiedPort;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class LiedPortStub implements LiedPort {

    private final Map<String, Lied> lieder = new HashMap<>();
    private final Map<String, byte[]> bytestreams = new HashMap<>();

    @Override
    public Lied ladeLied(Lied.Id songId) {
        System.out.println("Load Lied Port Stub!");
        return lieder.get(songId.id());
    }

    @Override
    public long zähleLieder() {
        return lieder.size();
    }

    @Override
    public Lied.Id fügeLiedHinzu(Lied lied, InputStream inputStream) throws IOException {
        lied.setId(new Lied.Id(UUID.randomUUID().toString()));
        bytestreams.put(lied.getId().id(), inputStream.readAllBytes());
        lieder.put(lied.getId().id(),  lied);
        return lied.getId();
    }

    @Override
    public void löscheDatenbank() {
        lieder.clear();
    }

    @Override
    public InputStream ladeLiedStream(Lied.Id liedId) {
        return new ByteArrayInputStream(bytestreams.get(liedId.id()));
    }

    @Override
    public void löscheLied(Lied.Id id) {
        lieder.remove(id.id());
    }
}
