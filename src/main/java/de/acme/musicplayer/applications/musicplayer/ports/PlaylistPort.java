package de.acme.musicplayer.applications.musicplayer.ports;

import de.acme.musicplayer.applications.musicplayer.domain.model.Playlist;
import de.acme.musicplayer.common.BenutzerId;
import de.acme.musicplayer.common.PlaylistId;
import de.acme.musicplayer.common.TenantId;

public interface PlaylistPort {

    Playlist lade(BenutzerId benutzername, Playlist.Name name, TenantId tenantId);

    Playlist lade(PlaylistId playlistId, TenantId tenantId);

    PlaylistId erstellePlaylist(BenutzerId benutzername, Playlist.Name name, TenantId tenantId);

    void löscheDatenbank(TenantId tenantId);

    void speichere(Playlist playlist, TenantId tenantId);
}
