package de.acme.musicplayer.application.ports;

import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;

public interface PlaylistPort {

    void addSongToPlaylist(Lied.LiedId liedId, Playlist.PlaylistId playlistId);

    Playlist lade(String benutzername, String playlistName);
}
