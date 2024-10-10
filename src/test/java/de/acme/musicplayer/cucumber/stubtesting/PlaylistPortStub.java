package de.acme.musicplayer.cucumber.stubtesting;

import de.acme.musicplayer.application.domain.model.Benutzer;
import de.acme.musicplayer.application.domain.model.Lied;
import de.acme.musicplayer.application.domain.model.Playlist;
import de.acme.musicplayer.application.ports.PlaylistPort;

import java.util.HashMap;
import java.util.Map;


public class PlaylistPortStub implements PlaylistPort {

    private final Map<Playlist.Id, Playlist> playlists = new HashMap<>();

    @Override
    public void addSongToPlaylist(Lied.Id liedId, Playlist.Id playlistId) {
        playlists.get(playlistId).liedHinzufügen(liedId);
    }

    @Override
    public Playlist lade(Playlist.Id playlistId) {
        Playlist currentPlaylist = playlists.get(playlistId);
        if (currentPlaylist == null) {
            throw new IllegalArgumentException(String.format("Playlist %s not found", playlistId));
        } else {
            return currentPlaylist;
        }
    }

    @Override
    public Playlist lade(Benutzer.Id benutzer, Playlist.Name playlistName) {
        return lade(new Playlist.Id(benutzer.Id(), playlistName.name()));
    }

    @Override
    public Playlist.Id erstellePlaylist(Benutzer.Id benutzer, Playlist.Name name) {
        Playlist.Id playlistId = new Playlist.Id(benutzer.Id(), name.name());
        if (playlists.containsKey(playlistId)) {
            return playlists.get(playlistId).getId();
        } else {
            Playlist playlist = new Playlist(benutzer, name);
            playlists.put(playlist.getId(), playlist);
            return playlist.getId();
        }
    }

    @Override
    public void löscheDatenbank() {
        playlists.clear();
    }
}
