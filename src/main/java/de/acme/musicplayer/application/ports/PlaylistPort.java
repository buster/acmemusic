package de.acme.musicplayer.application.ports;

import de.acme.musicplayer.application.domain.model.Playlist;

public interface PlaylistPort {

    void addSongToPlaylist(String songId, String playlistId);

    Playlist lade(String benutzername, String playlistName);
}
