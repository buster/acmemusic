package de.acme.musicplayer.applications.musicplayer.adapters.jdbc.playlist;

import de.acme.musicplayer.applications.musicplayer.domain.model.Playlist;
import de.acme.musicplayer.applications.musicplayer.ports.PlaylistPort;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.PlaylistId;
import de.acme.musicplayer.common.TenantId;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


public class PlaylistPortStub implements PlaylistPort {

    private final Map<Pair<PlaylistId, TenantId>, Playlist> playlists = new ConcurrentHashMap<>();

    private Playlist loadPlaylistByIdAndTenant(PlaylistId playlistId, TenantId tenantId) {
        Optional<Map.Entry<Pair<PlaylistId, TenantId>, Playlist>> pairPlaylistEntry1 = playlists.entrySet().stream()
                .filter(
                        pairPlaylistEntry -> pairPlaylistEntry.getKey().getLeft().equals(playlistId)
                                && pairPlaylistEntry.getKey().getRight().equals(tenantId)
                )
                .findFirst();
        //noinspection OptionalIsPresent
        if (pairPlaylistEntry1.isEmpty()) {
            return null;
        } else {
            return pairPlaylistEntry1.get().getValue();
        }
    }

    @Override
    public Playlist lade(PlaylistId playlistId, TenantId tenantId) {
        Playlist currentPlaylist = loadPlaylistByIdAndTenant(playlistId, tenantId);
        if (currentPlaylist == null) {
            throw new IllegalArgumentException(String.format("Playlist %s not found", playlistId));
        } else {
            return currentPlaylist;
        }
    }

    @Override
    public Playlist lade(BenutzerId benutzer, Playlist.Name playlistName, TenantId tenantId) {
        return lade(new PlaylistId(benutzer.Id(), playlistName.name()), tenantId);
    }

    @Override
    public PlaylistId erstellePlaylist(BenutzerId benutzer, Playlist.Name name, TenantId tenantId) {
        PlaylistId playlistId = new PlaylistId(benutzer.Id(), name.name());
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
        for (Pair<PlaylistId, TenantId> idTenantIdPair : playlists.keySet()) {
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
