package de.acme.musicplayer.application.usecases;

public interface LiedZuPlaylistHinzufügenUseCase {
    void addSongToPlaylist(String songId, String playlistId);
}
