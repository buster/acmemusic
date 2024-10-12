package de.acme.musicplayer.cucumber.stubtesting;

import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.ports.PlaylistPort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PlaylistPortStub implements PlaylistPort {

    private final Map<String, List<Playlist>> playlists = new HashMap<>();

    @Override
    public void addSongToPlaylist(Lied.LiedId liedId, Playlist.PlaylistId playlistId) {
        playlists.values().stream().flatMap(List::stream)
                .filter(p -> p.getId().equals(playlistId))
                .findFirst()
                .ifPresent(p -> p.liedHinzufügen(liedId));
    }

    @Override
    public Playlist lade(String benutzername, String playlistName) {
        List<Playlist> userPlaylists = playlists.get(benutzername);
        if (userPlaylists == null) {
            return newPlaylist(benutzername, playlistName);
        }
        return userPlaylists.stream()
                .filter(p ->
                        p.getId().equals(new Playlist.PlaylistId(benutzername, playlistName)))
                .findFirst()
                .orElse(newPlaylist(benutzername, playlistName));
    }

    private Playlist newPlaylist(String benutzername, String playlistName) {
        if (playlists.containsKey(benutzername)) {
            List<Playlist> userPlaylists = playlists.get(benutzername);
            Playlist playlist = new Playlist(benutzername, playlistName);
            userPlaylists.add(playlist);
            playlists.put(benutzername, userPlaylists);
            return playlist;
        }
        Playlist playlist = new Playlist(benutzername, playlistName);
        playlists.put(benutzername, new ArrayList<>(List.of(playlist)));
        return playlist;
    }
}
