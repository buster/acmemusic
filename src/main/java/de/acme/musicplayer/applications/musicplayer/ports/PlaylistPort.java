package de.acme.musicplayer.applications.musicplayer.ports;

import de.acme.musicplayer.applications.users.domain.model.Benutzer;
import de.acme.musicplayer.applications.musicplayer.domain.model.Playlist;
import de.acme.musicplayer.applications.musicplayer.domain.model.TenantId;

public interface PlaylistPort {

    Playlist lade(Benutzer.Id benutzername, Playlist.Name name, TenantId tenantId);

    Playlist lade(Playlist.Id playlistId, TenantId tenantId);

    Playlist.Id erstellePlaylist(Benutzer.Id benutzername, Playlist.Name name, TenantId tenantId);

    void löscheDatenbank(TenantId tenantId);

    void speichere(Playlist playlist, TenantId tenantId);
}
