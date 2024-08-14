package de.acme.musicplayer.application.ports;

public interface AddSongToPlaylistPort {

    void addSongToPlaylist(String songId, String playlistId);
}
