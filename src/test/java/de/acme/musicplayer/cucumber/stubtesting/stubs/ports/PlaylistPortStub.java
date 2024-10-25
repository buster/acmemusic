package de.acme.musicplayer.cucumber.stubtesting.stubs.ports;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.domain.model.TenantId;
import de.acme.musicplayer.application.ports.PlaylistPort;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


public class PlaylistPortStub implements PlaylistPort {

    private final Map<Pair<Playlist.Id, TenantId>, Playlist> playlists = new ConcurrentHashMap<>();

    private Playlist loadPlaylistByIdAndTenant(Playlist.Id playlistId, TenantId tenantId) {
        Optional<Map.Entry<Pair<Playlist.Id, TenantId>, Playlist>> pairPlaylistEntry1 = playlists.entrySet().stream()
                .filter(
                        pairPlaylistEntry -> pairPlaylistEntry.getKey().getLeft().equals(playlistId)
                                && pairPlaylistEntry.getKey().getRight().equals(tenantId)
                )
                .findFirst();
        if (pairPlaylistEntry1.isEmpty()) {
            return null;
        } else {
            return pairPlaylistEntry1.get().getValue();
        }
    }

    @Override
    public Playlist lade(Playlist.Id playlistId, TenantId tenantId) {
        Playlist currentPlaylist = loadPlaylistByIdAndTenant(playlistId, tenantId);
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
        if (loadPlaylistByIdAndTenant(playlistId, tenantId) != null) {
            return loadPlaylistByIdAndTenant(playlistId, tenantId).getId();
        } else {
            Playlist playlist = new Playlist(benutzer, name);
            playlists.put(Pair.of(playlist.getId(), tenantId), playlist);
            return playlist.getId();
        }
    }

    @Override
    public void löscheDatenbank(TenantId tenantId) {
        for (Pair<Playlist.Id, TenantId> idTenantIdPair : playlists.keySet()) {
            if (idTenantIdPair.getRight().equals(tenantId)) {
                playlists.remove(idTenantIdPair);
            }
        }
    }

    @Override
    public void speichere(Playlist playlist, TenantId tenantId) {
        playlists.put(Pair.of(playlist.getId(), tenantId), playlist);
    }
}
