package de.acme.musicplayer.application.usecases;

public interface AddSongToPlaylistUseCase {
    void addSongToPlaylist(String songId, String playlistId);
}
