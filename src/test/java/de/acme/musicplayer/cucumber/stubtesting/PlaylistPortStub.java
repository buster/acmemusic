package de.acme.musicplayer.cucumber.stubtesting;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.domain.model.TenantId;
import de.acme.musicplayer.application.ports.PlaylistPort;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class PlaylistPortStub implements PlaylistPort {

    private final Map<Playlist.Id, Playlist> playlists = new ConcurrentHashMap<>();

    @Override
    public void fügeLiedHinzu(Lied.Id liedId, Playlist.Id playlistId, TenantId tenantId) {
        playlists.get(playlistId).liedHinzufügen(liedId);
    }

    @Override
    public Playlist lade(Playlist.Id playlistId, TenantId tenantId) {
        Playlist currentPlaylist = playlists.get(playlistId);
        if (currentPlaylist == null) {
            throw new IllegalArgumentException(String.format("Playlist %s not found", playlistId));
        } else {
            return currentPlaylist;
        }
    }

    @Override
    public Playlist lade(Benutzer.Id benutzer, Playlist.Name playlistName, TenantId tenantId) {
        return lade(new Playlist.Id(benutzer.Id(), playlistName.name()), tenantId);
    }

    @Override
    public Playlist.Id erstellePlaylist(Benutzer.Id benutzer, Playlist.Name name, TenantId tenantId) {
        Playlist.Id playlistId = new Playlist.Id(benutzer.Id(), name.name());
        if (playlists.containsKey(playlistId)) {
            return playlists.get(playlistId).getId();
        } else {
            Playlist playlist = new Playlist(benutzer, name);
            playlists.put(playlist.getId(), playlist);
            return playlist.getId();
        }
    }

    @Override
    public void löscheDatenbank(TenantId tenantId) {
        playlists.clear();
    }
}
