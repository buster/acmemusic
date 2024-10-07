package de.acme.musicplayer.application.ports;

public interface PlaylistPort {

    void addSongToPlaylist(String songId, String playlistId);
}
