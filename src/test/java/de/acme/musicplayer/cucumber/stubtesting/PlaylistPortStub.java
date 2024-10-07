package de.acme.musicplayer.cucumber.stubtesting;

import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.ports.PlaylistPort;

import java.util.HashMap;
import java.util.Map;

public class PlaylistPortStub implements PlaylistPort {

    private Map<String, Playlist> playlists = new HashMap<>();


    @Override
    public void addSongToPlaylist(String songId, String playlistId) {

    }

    @Override
    public Playlist lade(String benutzername, String playlistName) {
        Playlist playlist = playlists.get(playlistName);
        if (playlist == null) {
            return null;
        }
        if (playlist.getBesitzer().equals(benutzername)) {
            return playlist;
        }
        return null;
    }
}
