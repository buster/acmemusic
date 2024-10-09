package de.acme.musicplayer.application.ports;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;

public interface PlaylistPort {

    void addSongToPlaylist(Lied.LiedId liedId, Playlist.PlaylistId playlistId);

    Playlist lade(Benutzer.Id benutzername, Playlist.Name name);

    Playlist lade(Playlist.PlaylistId playlistId);

    Playlist.PlaylistId erstellePlaylist(Benutzer.Id benutzername, Playlist.Name name);
}
