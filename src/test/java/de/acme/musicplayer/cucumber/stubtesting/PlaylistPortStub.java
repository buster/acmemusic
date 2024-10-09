package de.acme.musicplayer.cucumber.stubtesting;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.ports.PlaylistPort;

import java.util.HashMap;
import java.util.Map;


public class PlaylistPortStub implements PlaylistPort {

    private final Map<Playlist.PlaylistId, Playlist> playlists = new HashMap<>();

    @Override
    public void addSongToPlaylist(Lied.LiedId liedId, Playlist.PlaylistId playlistId) {
        playlists.get(playlistId).liedHinzufügen(liedId);
    }

    @Override
    public Playlist lade(Playlist.PlaylistId playlistId) {
        Playlist currentPlaylist = playlists.get(playlistId);
        if (currentPlaylist == null) {
            throw new IllegalArgumentException(String.format("Playlist %s not found", playlistId));
        } else {
            return currentPlaylist;
        }
    }

    @Override
    public Playlist lade(Benutzer.Id benutzer, Playlist.Name playlistName) {
        return lade(new Playlist.PlaylistId(benutzer.Id(), playlistName.name()));
    }

    @Override
    public Playlist.PlaylistId erstellePlaylist(Benutzer.Id benutzer, Playlist.Name name) {
        Playlist.PlaylistId playlistId = new Playlist.PlaylistId(benutzer.Id(), name.name());
        if (playlists.containsKey(playlistId)) {
            return playlists.get(playlistId).getId();
        } else {
            Playlist playlist = new Playlist(benutzer, name);
            playlists.put(playlist.getId(), playlist);
            return playlist.getId();
        }
    }
}
