package de.acme.musicplayer.application.usecases;

public interface LiedZuPlaylistHinzufügenUseCase {
    void addSongToPlaylist(String benutzername, String songId, String playlistName);
}
