package de.acme.musicplayer.application.ports;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;

public interface PlaylistPort {

    void addSongToPlaylist(Lied.Id liedId, Playlist.Id playlistId);

    Playlist lade(Benutzer.Id benutzername, Playlist.Name name);

    Playlist lade(Playlist.Id playlistId);

    Playlist.Id erstellePlaylist(Benutzer.Id benutzername, Playlist.Name name);

    void löscheDatenbank();
}
